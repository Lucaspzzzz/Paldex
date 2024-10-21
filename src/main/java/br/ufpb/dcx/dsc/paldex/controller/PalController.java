package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.PalDTO;
import br.ufpb.dcx.dsc.paldex.DTO.PalDTOResponse;
import br.ufpb.dcx.dsc.paldex.model.Pal;
import br.ufpb.dcx.dsc.paldex.service.PalService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PalController {

    private final PalService palService;

    private final ModelMapper modelMapper;

    public PalController(PalService palService, ModelMapper modelMapper) {
        this.palService = palService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/pals")
    @ResponseStatus(HttpStatus.OK)
    public List<PalDTOResponse> listPals() {
        return palService.getAllPals().stream().map(this::convertToDTO).toList();
    }

    @GetMapping("/pals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PalDTOResponse getPalById(@PathVariable Long id) {
        return convertToDTO(palService.getPal(id));
    }

    @PostMapping("/pals")
    @ResponseStatus(HttpStatus.CREATED)
    public PalDTOResponse createPal(@RequestBody PalDTO palDto) {
        return convertToDTO(palService.savePal(convertToEntity(palDto)));
    }

    @PutMapping("/pals/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PalDTOResponse updatePal(@PathVariable Long id, @RequestBody PalDTO updatedPalDTO) {
        return convertToDTO(palService.updatePal(id, convertToEntity(updatedPalDTO)));
    }

    @DeleteMapping("/pals/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePal(@PathVariable Long id) {
        palService.deletePal(id);
    }

    private PalDTOResponse convertToDTO(Pal p) {
        return modelMapper.map(p, PalDTOResponse.class);
    }

    private Pal convertToEntity(PalDTO palDTO) {
        return modelMapper.map(palDTO, Pal.class);
    }
}