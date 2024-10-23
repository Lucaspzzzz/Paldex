package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.DTO.ChangePasswordDTO;
import br.ufpb.dcx.dsc.paldex.DTO.EditAccountDTO;
import br.ufpb.dcx.dsc.paldex.DTO.LoginRequestDTO;
import br.ufpb.dcx.dsc.paldex.DTO.LoginResponseDTO;
import br.ufpb.dcx.dsc.paldex.exception.ItemNotFoundException;
import br.ufpb.dcx.dsc.paldex.exception.InvalidDataException;
import br.ufpb.dcx.dsc.paldex.exception.RoleNotFoundException;
import br.ufpb.dcx.dsc.paldex.model.Role;
import br.ufpb.dcx.dsc.paldex.model.RoleName;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.repository.RoleRepository;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;
import br.ufpb.dcx.dsc.paldex.security.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Register a new user
    public User register(User user) {
        return userService.createUser(user);
    }

    // Login a user
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new InvalidDataException("User not registered with this email."));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtTokenService.generateToken(userDetails);

            return new LoginResponseDTO(jwt);

        } catch (BadCredentialsException ex) {
            throw new InvalidDataException("Email or password is incorrect.");
        }
    }
    // Change password
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User currentUser = getAuthenticatedUser();

        if (passwordEncoder.matches(changePasswordDTO.getNewPassword(), currentUser.getPassword())) {
            throw new InvalidDataException("The new password cannot be the same as the old password.");
        }

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), currentUser.getPassword())) {
            throw new InvalidDataException("Old password is incorrect.");
        }

        currentUser.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(currentUser);
    }


    public User editAccount(EditAccountDTO editAccountDTO) {
        // Obtém o usuário autenticado
        User currentUser = getAuthenticatedUser();

        // Validações de email e username para garantir que não estão sendo usados por outro usuário
        if (!editAccountDTO.getEmail().equals(currentUser.getEmail()) && userRepository.existsByEmail(editAccountDTO.getEmail())) {
            throw new InvalidDataException("Email já está em uso.");
        }

        if (!editAccountDTO.getUsername().equals(currentUser.getUsername()) && userRepository.existsByUsername(editAccountDTO.getUsername())) {
            throw new InvalidDataException("Nome de usuário já está em uso.");
        }

        // Atualiza as informações do usuário atual
        currentUser.setEmail(editAccountDTO.getEmail());
        currentUser.setUsername(editAccountDTO.getUsername());
        currentUser.setName(editAccountDTO.getName());

        return userRepository.save(currentUser);
    }

    public void deleteAccount() {
        User currentUser = getAuthenticatedUser();
        userRepository.delete(currentUser);
    }
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("User not found."));
    }
}
