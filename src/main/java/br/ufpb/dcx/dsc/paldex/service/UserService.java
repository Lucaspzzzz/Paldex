package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.config.CustomUserDetails;
import br.ufpb.dcx.dsc.paldex.exception.*;
import br.ufpb.dcx.dsc.paldex.model.Photo;
import br.ufpb.dcx.dsc.paldex.model.Role;
import br.ufpb.dcx.dsc.paldex.model.RoleName;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.repository.PhotoRepository;
import br.ufpb.dcx.dsc.paldex.repository.RoleRepository;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String DEFAULT_PHOTO_URL = "www.exemplo.com/foto.png";

    public UserService(UserRepository userRepository, PhotoRepository photoRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User " + userId + " not found!"));
    }

    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidDataException("Email '" + user.getEmail() + "' is already in use.");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new InvalidDataException("Username '" + user.getUsername() + "' is already in use.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getPhoto() == null) {
            Photo defaultPhoto = new Photo(DEFAULT_PHOTO_URL);
            photoRepository.save(defaultPhoto);
            user.setPhoto(defaultPhoto);
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException("Role USER not found"));
            user.setRoles(Collections.singletonList(userRole));
        }

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User u) {
        checkIfAdmin();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("Usuário " + userId + " não encontrado!"));

        if (user.getEmail().equals(u.getEmail()) &&
                user.getUsername().equals(u.getUsername()) &&
                user.getName().equals(u.getName()) &&
                (u.getPassword() == null || passwordEncoder.matches(u.getPassword(), user.getPassword())) &&
                ((u.getPhoto() == null && user.getPhoto() == null) ||
                        (u.getPhoto() != null && user.getPhoto() != null && u.getPhoto().getPhotoURL().equals(user.getPhoto().getPhotoURL())))) {

            throw new InvalidDataException("Você precisa alterar algo para atualizar.");
        }

        // Validações para garantir que o email e username não estão em uso
        if (!user.getEmail().equals(u.getEmail()) && userRepository.existsByEmail(u.getEmail())) {
            throw new InvalidDataException("Email já está em uso.");
        }

        if (!user.getUsername().equals(u.getUsername()) && userRepository.existsByUsername(u.getUsername())) {
            throw new InvalidDataException("Nome de usuário já está em uso.");
        }

        // Atualiza as informações do usuário
        user.setEmail(u.getEmail());
        user.setName(u.getName());

        if (u.getUsername() != null && !u.getUsername().isEmpty()) {
            user.setUsername(u.getUsername());
        }

        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(u.getPassword()));
        }

        if (u.getPhoto() != null) {
            user.setPhoto(u.getPhoto());
        }

        return userRepository.save(user);
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidDataException("Usuário não autenticado.");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    public void deleteUser(Long userId) {
        User authenticatedUser = getAuthenticatedUser();

        if (authenticatedUser.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN))) {
            if (authenticatedUser.getUserId().equals(userId)) {
                throw new InvalidDataException("Admin cannot delete their own account.");
            }
            userRepository.deleteById(userId);
        }

        else if (authenticatedUser.getUserId().equals(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ForbiddenActionException("You are not allowed to delete other users' accounts.");
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidDataException("No user is currently authenticated.");
        }

        // O principal pode ser uma string ou CustomUserDetails, dependendo do contexto
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getUser(); // Obter o usuário diretamente do CustomUserDetails
        } else {
            throw new InvalidDataException("Unable to retrieve the authenticated user.");
        }
    }

    private void checkIfAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ItemNotFoundException("Authenticated user not found.");
        }

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        if (userDetails == null || userDetails.getUser() == null) {
            throw new ItemNotFoundException("Authenticated user not found.");
        }

        if (!userDetails.getUser().getRoles().stream()
                .anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN))) {
            throw new ForbiddenActionException("Only admins can perform this action.");
        }
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("User with email " + email + " not found"));
    }


}





