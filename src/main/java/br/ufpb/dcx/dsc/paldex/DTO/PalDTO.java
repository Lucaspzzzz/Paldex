package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PalDTO {

    private Long palId;

    private String name;

    private String title;

    private String rarity;

    private Set<Elements> elements;

    private Set<Drop> drops;

    private Statistic statistic;

    private ActiveSkill partnerSkill;

    private Skill passiveSkill;

    private ActiveSkill activeSkill;

    private Collection<Work> works;

    private Photo photo;
}
