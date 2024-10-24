package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.exception.ItemNotFoundException;
import br.ufpb.dcx.dsc.paldex.model.*;
import br.ufpb.dcx.dsc.paldex.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PalService {

    private final PalRepository palRepository;

    private final DropRepository dropRepository;

    private final StatisticRepository statisticRepository;

    private final ActiveSkillRepository activeSkillRepository;

    private final SkillRepository skillRepository;

    private final WorkRepository workRepository;

    private final PhotoRepository photoRepository;

    public PalService(
            PalRepository palRepository,
            DropRepository dropRepository,
            StatisticRepository statisticRepository,
            ActiveSkillRepository activeSkillRepository,
            SkillRepository skillRepository,
            WorkRepository workRepository,
            PhotoRepository photoRepository) {
        this.palRepository = palRepository;
        this.dropRepository = dropRepository;
        this.statisticRepository = statisticRepository;
        this.activeSkillRepository = activeSkillRepository;
        this.skillRepository = skillRepository;
        this.workRepository = workRepository;
        this.photoRepository = photoRepository;
    }

    public Pal savePal(Pal pal) {
        // Salva os drops
        Set<Drop> drops = pal.getDrops();
        if (drops != null) {
            dropRepository.saveAll(drops);
        }

        // Salva a estatística
        Statistic statistic = pal.getStatistic();
        if (statistic != null) {
            statisticRepository.save(statistic);
        }

        // Salva a partnerSkill
        ActiveSkill partnerSkill = pal.getPartnerSkill();
        if (partnerSkill != null) {
            activeSkillRepository.save(partnerSkill);
        }

        // Salva a passiveSkill
        Skill passiveSkill = pal.getPassiveSkill();
        if (passiveSkill != null) {
            skillRepository.save(passiveSkill);
        }

        // Salva a activeSkill
        ActiveSkill activeSkill = pal.getActiveSkill();
        if (activeSkill != null) {
            activeSkillRepository.save(activeSkill);
        }

        // Salva os works
        if (pal.getWorks() != null) {
            workRepository.saveAll(pal.getWorks());
        }

        if (pal.getPhoto() != null){
            photoRepository.save(pal.getPhoto());
        }

        return palRepository.save(pal);
    }

    public List<Pal> getAllPals() {
        return palRepository.findAll();
    }

    @Transactional
    public Pal getPal(Long id) {
        return palRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Pal não encontrado"));
    }

    public Pal updatePal(Long id, Pal updatedPal) {
        Optional<Pal> palOpt = palRepository.findById(id);
        if (palOpt.isPresent()) {
            Pal pal = palOpt.get();
            pal.setName(updatedPal.getName());
            pal.setTitle(updatedPal.getTitle());
            pal.setRarity(updatedPal.getRarity());
            pal.setElements(updatedPal.getElements());

            Set<Drop> drops = updatedPal.getDrops();
            if (drops != null) {
                dropRepository.saveAll(drops);
                pal.setDrops(drops);
            }

            Statistic statistic = updatedPal.getStatistic();
            if (statistic != null) {
                statisticRepository.save(statistic);
                pal.setStatistic(statistic);
            }

            ActiveSkill partnerSkill = updatedPal.getPartnerSkill();
            if (partnerSkill != null) {
                activeSkillRepository.save(partnerSkill);
                pal.setPartnerSkill(partnerSkill);
            }

            Skill passiveSkill = updatedPal.getPassiveSkill();
            if (passiveSkill != null) {
                skillRepository.save(passiveSkill);
                pal.setPassiveSkill(passiveSkill);
            }

            ActiveSkill activeSkill = updatedPal.getActiveSkill();
            if (activeSkill != null) {
                activeSkillRepository.save(activeSkill);
                pal.setActiveSkill(activeSkill);
            }

            if (updatedPal.getWorks() != null) {
                workRepository.saveAll(updatedPal.getWorks());
                pal.setWorks(updatedPal.getWorks());
            }

            return palRepository.save(pal);
        } else {
            throw new ItemNotFoundException("Pal não encontrado");
        }
    }

    public void deletePal(Long id) {
        palRepository.deleteById(id);
    }
}