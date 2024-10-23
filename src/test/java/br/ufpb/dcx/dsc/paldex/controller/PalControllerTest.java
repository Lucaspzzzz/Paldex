package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.*;
import br.ufpb.dcx.dsc.paldex.model.*;
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
import java.util.HashSet;
import java.util.Set;

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
        // Configurando o conjunto de elementos
        Set<Elements> elements = new HashSet<>(Arrays.asList(Elements.WATER, Elements.FIRE));

        // Inicializando o objeto Pal (modelo)
        pal = new Pal();
        pal.setPalId(1L);
        pal.setName("Pal Example");
        pal.setTitle("The Brave");
        pal.setRarity("Common");
        pal.setElements(elements);

        // Inicializando o DTO Pal (usado para request/response)
        palDTO = new PalDTO();
        palDTO.setName("Pal Example");
        palDTO.setTitle("The Brave");
        palDTO.setRarity("Common");
        palDTO.setElements(elements);

        // Configurando o objeto DropDTO e adicionando ao palDTO
        DropDTO dropDTO = new DropDTO();
        dropDTO.setDropId(1L);
        dropDTO.setName("Magic Stone");
        Set<DropDTO> dropsDTO = Set.of(dropDTO);
        palDTO.setDrops(dropsDTO);

        // Configurando o objeto StatisticDTO e adicionando ao palDTO
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setId(1L);
        statisticDTO.setHp(100);
        statisticDTO.setRaceSpeed(50);
        statisticDTO.setBodyAttack(100);
        statisticDTO.setDefense(688);
        statisticDTO.setPrice(3455);
        statisticDTO.setStamina(456);
        statisticDTO.setCreationSpeed(455);
        statisticDTO.setDistanceAttack(234);
        statisticDTO.setMountedSprintSpeed(656);
        statisticDTO.setSlowWalkingSpeed(234);
        statisticDTO.setSupport(344);
        palDTO.setStatistic(statisticDTO);

        // Configurando a habilidade de parceiro (ActiveSkillDTO) e adicionando ao palDTO
        ActiveSkillDTO partnerSkillDTO = new ActiveSkillDTO();
        partnerSkillDTO.setSkillId(1L);
        partnerSkillDTO.setName("Partner Attack");
        partnerSkillDTO.setPower(100);
        partnerSkillDTO.setCooldown(5);
        partnerSkillDTO.setLevel(1);
        partnerSkillDTO.setElement(Elements.FIRE);
        partnerSkillDTO.setRange(10);
        palDTO.setPartnerSkill(partnerSkillDTO);

        // Configurando a habilidade passiva (SkillDTO) e adicionando ao palDTO
        SkillDTO passiveSkillDTO = new SkillDTO();
        passiveSkillDTO.setSkillId(2L);
        passiveSkillDTO.setName("Passive Defense");
        palDTO.setPassiveSkill(passiveSkillDTO);

        // Configurando a habilidade ativa (ActiveSkillDTO) e adicionando ao palDTO
        ActiveSkillDTO activeSkillDTO = new ActiveSkillDTO();
        activeSkillDTO.setSkillId(3L);
        activeSkillDTO.setName("Active Slash");
        activeSkillDTO.setPower(150);
        activeSkillDTO.setCooldown(4);
        activeSkillDTO.setLevel(2);
        activeSkillDTO.setElement(Elements.ICE);
        activeSkillDTO.setRange(15);
        palDTO.setActiveSkill(activeSkillDTO);

        // Configurando o trabalho (WorkDTO) e adicionando ao palDTO
        WorkDTO workDTO = new WorkDTO();
        workDTO.setWorkId(1L);
        workDTO.setType("Builder");
        palDTO.setWorks(Set.of(workDTO));

        // Configurando a foto (PhotoDTO) e adicionando ao palDTO
        Photo photo = new Photo();
        photo.setPhotoId(1L);
        photo.setPhotoURL("http://example.com/photo.jpg");
        palDTO.setPhoto(photo);

        // Criar instância do PalDTOResponse com as entidades corretas do modelo para asserção
        Drop drop = new Drop();
        drop.setDropId(1L);
        drop.setName("Magic Stone");

        Statistic statistic = new Statistic();
        statistic.setId(1L);
        statistic.setHp(100);
        statistic.setRaceSpeed(50);
        statistic.setBodyAttack(100);
        statistic.setDefense(688);
        statistic.setPrice(3455);
        statistic.setStamina(456);
        statistic.setCreationSpeed(455);
        statistic.setDistanceAttack(234);
        statistic.setMountedSprintSpeed(656);
        statistic.setSlowWalkingSpeed(234);
        statistic.setSupport(344);

        ActiveSkill partnerSkill = new ActiveSkill();
        partnerSkill.setSkillId(1L);
        partnerSkill.setName("Partner Attack");
        partnerSkill.setPower(100);
        partnerSkill.setCooldown(5);
        partnerSkill.setLevel(1);
        partnerSkill.setElement(Elements.FIRE);
        partnerSkill.setRange(10);

        Skill passiveSkill = new Skill();
        passiveSkill.setSkillId(2L);
        passiveSkill.setName("Passive Defense");

        ActiveSkill activeSkill = new ActiveSkill();
        activeSkill.setSkillId(3L);
        activeSkill.setName("Active Slash");
        activeSkill.setPower(150);
        activeSkill.setCooldown(4);
        activeSkill.setLevel(2);
        activeSkill.setElement(Elements.ICE);
        activeSkill.setRange(15);

        Work work = new Work();
        work.setWorkId(1L);
        work.setType("Builder");


        palDTOResponse = new PalDTOResponse();
        palDTOResponse.setPalId(1L);
        palDTOResponse.setName("Pal Example");
        palDTOResponse.setTitle("The Brave");
        palDTOResponse.setRarity("Common");
        palDTOResponse.setElements(elements);
        palDTOResponse.setDrops(Set.of(drop));
        palDTOResponse.setStatistic(statistic);
        palDTOResponse.setPartnerSkill(partnerSkill);
        palDTOResponse.setPassiveSkill(passiveSkill);
        palDTOResponse.setActiveSkill(activeSkill);
        palDTOResponse.setWorks(Set.of(work));
        palDTOResponse.setPhoto(photo);
    }



    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testListPals() throws Exception {
        Mockito.when(palService.getAllPals()).thenReturn(Arrays.asList(pal));
        Mockito.when(modelMapper.map(Mockito.any(Pal.class), Mockito.eq(PalDTOResponse.class))).thenReturn(palDTOResponse);

        mockMvc.perform(get("/api/pals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(palDTOResponse.getName()))
                .andExpect(jsonPath("$[0].title").value(palDTOResponse.getTitle()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testGetPalById() throws Exception {
        Mockito.when(palService.getPal(1L)).thenReturn(pal);
        Mockito.when(modelMapper.map(Mockito.any(Pal.class), Mockito.eq(PalDTOResponse.class))).thenReturn(palDTOResponse);

        mockMvc.perform(get("/api/pals/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(palDTOResponse.getName()))
                .andExpect(jsonPath("$.title").value(palDTOResponse.getTitle()));
    }

    @Test
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
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
    @WithMockUser(username = "admin@gmail.com", roles = {"ADMIN"})
    void testDeletePal() throws Exception {
        Mockito.doNothing().when(palService).deletePal(1L);

        mockMvc.perform(delete("/api/pals/1"))
                .andExpect(status().isNoContent());
    }
}
