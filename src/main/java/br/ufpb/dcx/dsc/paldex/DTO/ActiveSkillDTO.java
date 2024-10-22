package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.Elements;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActiveSkillDTO extends SkillDTO{

    @Min(value = 0, message = "Poder deve ser no mínimo 0")
    private int power;

    @Min(value = 0, message = "Cooldown deve ser no mínimo 0")
    private int cooldown;

    @Min(value = 1, message = "Nível deve ser no mínimo 1")
    private int level;

    @NotNull(message = "O elemento não pode ser nulo")
    private Elements element;

    @Min(value = 0, message = "Alcance deve ser no mínimo 0")
    private int range;
}
