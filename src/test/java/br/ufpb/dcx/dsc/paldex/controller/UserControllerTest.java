package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.UserDTO;
import br.ufpb.dcx.dsc.paldex.DTO.UserDTOResponse;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    private User user;
    private UserDTO userDTO;
    private UserDTOResponse userDTOResponse;

    @BeforeEach
    void setUp() {
        // Inicializando o objeto User (modelo)
        user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        user.setPassword("password");

        // Inicializando o DTO User (usado para request)
        userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("testuser@example.com");
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        // Inicializando o DTO UserResponse (usado para response)
        userDTOResponse = new UserDTOResponse();
        userDTOResponse.setUserId(1L);
        userDTOResponse.setName("Test User");
        userDTOResponse.setEmail("testuser@example.com");
        userDTOResponse.setUsername("testuser");
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testListUsers() throws Exception {
        Mockito.when(userService.listUsers()).thenReturn(List.of(user));
        Mockito.when(modelMapper.map(Mockito.any(User.class), Mockito.eq(UserDTOResponse.class))).thenReturn(userDTOResponse);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$[0].email").value(userDTOResponse.getEmail()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testGetUserById() throws Exception {
        Mockito.when(userService.getUser(1L)).thenReturn(user);
        Mockito.when(modelMapper.map(Mockito.any(User.class), Mockito.eq(UserDTOResponse.class))).thenReturn(userDTOResponse);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.email").value(userDTOResponse.getEmail()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testCreateUser() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(modelMapper.map(Mockito.any(User.class), Mockito.eq(UserDTOResponse.class))).thenReturn(userDTOResponse);
        Mockito.when(modelMapper.map(Mockito.any(UserDTO.class), Mockito.eq(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.email").value(userDTOResponse.getEmail()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testUpdateUser() throws Exception {
        Mockito.when(userService.updateUser(Mockito.eq(1L), Mockito.any(User.class))).thenReturn(user);
        Mockito.when(modelMapper.map(Mockito.any(User.class), Mockito.eq(UserDTOResponse.class))).thenReturn(userDTOResponse);
        Mockito.when(modelMapper.map(Mockito.any(UserDTO.class), Mockito.eq(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDTOResponse.getName()))
                .andExpect(jsonPath("$.email").value(userDTOResponse.getEmail()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
