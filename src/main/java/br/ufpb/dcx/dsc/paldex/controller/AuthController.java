package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.*;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.service.AuthService;
import br.ufpb.dcx.dsc.paldex.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public AuthController(AuthService authService, ModelMapper modelMapper,UserService userService) {
        this.authService = authService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTOResponse> register(@Valid @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = authService.register(user);
        return ResponseEntity.ok(convertToDTO(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponse = authService.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        authService.changePassword(changePasswordDTO);
        return ResponseEntity.ok("Password updated successfully.");
    }

    @PutMapping("/edit-account")
    public ResponseEntity<UserDTOResponse> editAccount(@Valid @RequestBody EditAccountDTO editAccountDTO) {
        User updatedUser = authService.editAccount(editAccountDTO);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    @DeleteMapping("/delete-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount() {
        User authenticatedUser = userService.getAuthenticatedUser(); // Obter o usuário autenticado
        userService.deleteUser(authenticatedUser.getUserId()); // Deletar a própria conta
    }


    private UserDTOResponse convertToDTO(User user) {
        return modelMapper.map(user, UserDTOResponse.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}

