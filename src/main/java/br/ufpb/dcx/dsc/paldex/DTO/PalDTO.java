package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.*;
import br.ufpb.dcx.dsc.paldex.validation.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class PalDTO {

    private Long palId;

    @NotBlank(message = "O nome não pode estar em branco")
    @Unique(fieldName = "name", domainClass = Pal.class)
    private String name;

    @NotBlank(message = "O titulo não pode estar em branco")
    @Unique(fieldName = "title", domainClass = Pal.class)
    private String title;

    @NotBlank(message = "A raridade não pode estar em branco")
    private String rarity;

    @NotEmpty(message = "Os elementos não podem ser vazios")
    private Set<Elements> elements;

    @NotNull(message = "Os drops não podem ser nulos")
    private Set<DropDTO> drops;

    @NotNull(message = "As estatísticas não podem ser nulas")
    private StatisticDTO statistic;

    @NotNull(message = "A habilidade de parceiro não pode ser nula")
    private ActiveSkillDTO partnerSkill;

    @NotNull(message = "A habilidade passiva não pode ser nula")
    private SkillDTO passiveSkill;

    @NotNull(message = "A habilidade ativa não pode ser nula")
    private ActiveSkillDTO activeSkill;

    @NotNull(message = "Os trabalhos não podem ser nulos")
    private Collection<WorkDTO> works;

    @NotNull(message = "A foto não pode ser nula")
    private Photo photo;

    private TeamDTO TeamDTO;
}
