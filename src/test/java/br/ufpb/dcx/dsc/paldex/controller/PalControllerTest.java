package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.PalDTO;
import br.ufpb.dcx.dsc.paldex.DTO.PalDTOResponse;
import br.ufpb.dcx.dsc.paldex.model.Pal;
import br.ufpb.dcx.dsc.paldex.service.PalService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PalService palService;

    @MockBean
    private ModelMapper modelMapper;

    private Pal pal;
    private PalDTO palDTO;
    private PalDTOResponse palDTOResponse;

    @BeforeEach
    void setUp() {
        pal = new Pal();
        pal.setPalId(1L);
        pal.setName("Pal Example");
        pal.setTitle("The Brave");

        palDTO = new PalDTO();
        palDTO.setName("Pal Example");
        palDTO.setTitle("The Brave");

        palDTOResponse = new PalDTOResponse();
        palDTOResponse.setName("Pal Example");
        palDTOResponse.setTitle("The Brave");
    }

    @Test
    @WithMockUser(username = "testuser")
    void testListPals() throws Exception {
        Mockito.when(palService.getAllPals()).thenReturn(Arrays.asList(pal));
        Mockito.when(modelMapper.map(Mockito.any(Pal.class), Mockito.eq(PalDTOResponse.class))).thenReturn(palDTOResponse);

        mockMvc.perform(get("/api/pals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(palDTOResponse.getName()))
                .andExpect(jsonPath("$[0].title").value(palDTOResponse.getTitle()));
    }

    @Test
    @WithMockUser(roles = "admin", password = "admin123")
    void testGetPalById() throws Exception {
        Mockito.when(palService.getPal(1L)).thenReturn(pal);
        Mockito.when(modelMapper.map(Mockito.any(Pal.class), Mockito.eq(PalDTOResponse.class))).thenReturn(palDTOResponse);

        mockMvc.perform(get("/api/pals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(palDTOResponse.getName()))
                .andExpect(jsonPath("$.title").value(palDTOResponse.getTitle()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testCreatePal() throws Exception {
        Mockito.when(palService.savePal(Mockito.any(Pal.class))).thenReturn(pal);
        Mockito.when(modelMapper.map(Mockito.any(Pal.class), Mockito.eq(PalDTOResponse.class))).thenReturn(palDTOResponse);
        Mockito.when(modelMapper.map(Mockito.any(PalDTO.class), Mockito.eq(Pal.class))).thenReturn(pal);

        mockMvc.perform(post("/api/pals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(palDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(palDTOResponse.getName()))
                .andExpect(jsonPath("$.title").value(palDTOResponse.getTitle()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testUpdatePal() throws Exception {
        Mockito.when(palService.updatePal(Mockito.eq(1L), Mockito.any(Pal.class))).thenReturn(pal);
        Mockito.when(modelMapper.map(Mockito.any(Pal.class), Mockito.eq(PalDTOResponse.class))).thenReturn(palDTOResponse);
        Mockito.when(modelMapper.map(Mockito.any(PalDTO.class), Mockito.eq(Pal.class))).thenReturn(pal);

        mockMvc.perform(put("/api/pals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(palDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(palDTOResponse.getName()))
                .andExpect(jsonPath("$.title").value(palDTOResponse.getTitle()));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testDeletePal() throws Exception {
        Mockito.doNothing().when(palService).deletePal(1L);

        mockMvc.perform(delete("/api/pals/1"))
                .andExpect(status().isNoContent());
    }
}
