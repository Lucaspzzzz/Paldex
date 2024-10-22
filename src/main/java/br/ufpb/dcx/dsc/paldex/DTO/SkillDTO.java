package br.ufpb.dcx.dsc.paldex.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDTO {
    private Long skillId;

    @NotBlank(message = "O nome da habilidade não pode estar vazio")
    private String name;

    @NotBlank(message = "A descrição da habilidade não pode estar vazia")
    private String description;
}
