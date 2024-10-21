package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.Elements;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ActiveSkillDTO extends SkillDTO{

    private int power;

    private int cooldown;

    private int level;

    private Elements element;

    private int range;
}
