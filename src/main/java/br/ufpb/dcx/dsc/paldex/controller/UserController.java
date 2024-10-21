package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.UserDTO;
import br.ufpb.dcx.dsc.paldex.DTO.UserDTOResponse;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@Validated
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public  UserController(UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path = "/users")
    List<UserDTOResponse> listUsers(){
        return userService.listUsers().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @GetMapping("/users/{userId}")
    public UserDTOResponse getUser(@PathVariable Long userId){
        User user = userService.getUser(userId);
        return convertToDTO(user);
    }


    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTOResponse createUser(@Valid @RequestBody UserDTO userDTO) {
        User u = convertToEntity(userDTO);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        User saved = userService.createUser(u);
        return convertToDTO(saved);
    }

    @PutMapping("/users/{userId}")
    public UserDTOResponse updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO){

        User u = convertToEntity(userDTO);
        User userUpdated = userService.updateUser(userId, u);
        return convertToDTO(userUpdated);
    }


    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    private UserDTOResponse convertToDTO(User u) {
        return modelMapper.map(u, UserDTOResponse.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
