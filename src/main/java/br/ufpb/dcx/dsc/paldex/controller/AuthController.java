package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.ChangePasswordDTO;
import br.ufpb.dcx.dsc.paldex.DTO.LoginRequestDTO;
import br.ufpb.dcx.dsc.paldex.DTO.LoginResponseDTO;
import br.ufpb.dcx.dsc.paldex.DTO.UserDTOResponse;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.security.JwtTokenService;
import br.ufpb.dcx.dsc.paldex.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTOResponse> register(@Valid @RequestBody User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);
        user.setPassword(encodedPassword);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(new UserDTOResponse(
                savedUser.getUserId(),
                savedUser.getName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhoto()
        ));
    }


    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userService.updateUser(currentUser.getUserId(), currentUser);
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(400).body("Old password is incorrect.");
        }
    }
}
