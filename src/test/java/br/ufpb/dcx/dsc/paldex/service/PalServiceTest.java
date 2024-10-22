package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.exception.ItemNotFoundException;
import br.ufpb.dcx.dsc.paldex.model.*;
import br.ufpb.dcx.dsc.paldex.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PalServiceTest {

    @Mock
    private PalRepository palRepository;

    @Mock
    private DropRepository dropRepository;

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private ActiveSkillRepository activeSkillRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private WorkRepository workRepository;

    @Mock
    private PhotoRepository photoRepository;

    @InjectMocks
    private PalService palService;

    private Pal pal;
    private Drop drop;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criação do Pal
        pal = new Pal();
        pal.setPalId(1L);
        pal.setName("Pal Example");
        pal.setTitle("The Brave");

        // Criação dos Drops
        drop = new Drop();
        drop.setDropId(1L);
        drop.setName("Drop Example");
        drop.setRate(30);
        Set<Drop> drops = new HashSet<>();
        drops.add(drop);
        pal.setDrops(drops);

        pal.setRarity("");

        pal.setElements(new HashSet<>());


        // Definindo a estatística
        Statistic statistic = new Statistic();
        statistic.setId(1L);
        statistic.setDefense(30);
        pal.setStatistic(statistic);

        // Definindo a activeSkill
        ActiveSkill activeSkill = new ActiveSkill();
        activeSkill.setSkillId(1L);
        activeSkill.setName("Fireball");
        pal.setActiveSkill(activeSkill);

        // Definindo a partnerSkill
        ActiveSkill partnerSkill = new ActiveSkill();
        partnerSkill.setSkillId(2L);
        partnerSkill.setName("Heal");
        pal.setPartnerSkill(partnerSkill);

        // Definindo a passiveSkill
        Skill passiveSkill = new Skill();
        passiveSkill.setSkillId(1L);
        passiveSkill.setName("Shield");
        pal.setPassiveSkill(passiveSkill);

        // Definindo os trabalhos (works)
        Work work = new Work();
        work.setWorkId(1L);
        Set<Work> works = new HashSet<>();
        works.add(work);
        pal.setWorks(works);

        // Definindo a foto
        Photo photo = new Photo();
        photo.setPhotoId(1L);
        photo.setPhotoURL("http://example.com/photo.jpg");
        pal.setPhoto(photo);
    }

    @Test
    void testGetPal() {
        when(palRepository.findById(1L)).thenReturn(Optional.of(pal));
        Pal result = palService.getPal(1L);
        assertNotNull(result);
        assertEquals("Pal Example", result.getName());
        assertEquals("The Brave", result.getTitle());
    }

    @Test
    void testListPals() {
        List<Pal> palList = Arrays.asList(pal);
        when(palRepository.findAll()).thenReturn(palList);
        List<Pal> result = palService.getAllPals();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pal Example", result.get(0).getName());
    }

    @Test
    void testSavePal() {
        when(palRepository.save(any(Pal.class))).thenReturn(pal);
        Pal result = palService.savePal(pal);
        assertNotNull(result);
        assertEquals("Pal Example", result.getName());
        verify(dropRepository, times(1)).saveAll(pal.getDrops());
    }

    @Test
    void testDeletePal() {
        palService.deletePal(1L);
        verify(palRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdatePal() {
        when(palRepository.findById(1L)).thenReturn(Optional.of(pal));

        Pal updatedPal = new Pal();
        updatedPal.setName("Pal Updated");
        updatedPal.setTitle("The Fearless");
        updatedPal.setRarity(pal.getRarity());
        updatedPal.setElements(pal.getElements());
        updatedPal.setDrops(pal.getDrops());
        updatedPal.setStatistic(pal.getStatistic());
        updatedPal.setPartnerSkill(pal.getPartnerSkill());
        updatedPal.setPassiveSkill(pal.getPassiveSkill());
        updatedPal.setActiveSkill(pal.getActiveSkill());
        updatedPal.setWorks(pal.getWorks());
        updatedPal.setPhoto(pal.getPhoto());

        when(palRepository.save(any(Pal.class))).thenReturn(updatedPal);

        Pal result = palService.updatePal(1L, updatedPal);

        assertNotNull(result);
        assertEquals("Pal Updated", result.getName());
        assertEquals("The Fearless", result.getTitle());


        verify(dropRepository, times(1)).saveAll(pal.getDrops());
        verify(statisticRepository, times(1)).save(pal.getStatistic());
        verify(activeSkillRepository, times(2)).save(any(ActiveSkill.class));
        verify(skillRepository, times(1)).save(pal.getPassiveSkill());
        verify(workRepository, times(1)).saveAll(pal.getWorks());
    }

    @Test
    void testUpdatePalNotFound() {
        when(palRepository.findById(1L)).thenReturn(Optional.empty());
        ItemNotFoundException thrown = assertThrows(
                ItemNotFoundException.class,
                () -> palService.updatePal(1L, pal)
        );

        assertEquals("Pal não encontrado", thrown.getMessage());
    }
}