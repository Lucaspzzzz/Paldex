package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class PalDTO {
    private Set<Long> teamIds;
    private Long palId;
    private String name;
    private String title;
    private String rarity;
    private Set<Elements> elements;
    private StatisticDTO statistic;
    private ActiveSkillDTO partnerSkill;
    private SkillDTO passiveSkill;
    private ActiveSkillDTO activeSkill;
    private Collection<WorkDTO> works;
    private Photo photo;
}
