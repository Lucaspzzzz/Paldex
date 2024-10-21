package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.exception.ItemNotFoundException;
import br.ufpb.dcx.dsc.paldex.model.Pal;
import br.ufpb.dcx.dsc.paldex.repository.PalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PalService {

    private final PalRepository palRepository;

    public PalService(PalRepository palRepository){
        this.palRepository = palRepository;
    }

    public Pal savePal(Pal pal) {
        return palRepository.save(pal);
    }

    public List<Pal> getAllPals() {
        return palRepository.findAll();
    }

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
            pal.setDrops(updatedPal.getDrops());
            pal.setStatistic(updatedPal.getStatistic());
            pal.setPartnerSkill(updatedPal.getPartnerSkill());
            pal.setActiveSkill(updatedPal.getActiveSkill());
            pal.setWorks(updatedPal.getWorks());
            pal.setPhoto(updatedPal.getPhoto());
            return palRepository.save(pal);
        }else {
            throw new ItemNotFoundException("Pal não encontrado");
        }
    }

    public void deletePal(Long id) {
        palRepository.deleteById(id);
    }

}