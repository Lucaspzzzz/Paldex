package br.ufpb.dcx.dsc.paldex.DTO;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long teamId;
    private String name;
    private Set<PalTeamDTO> pals;
}

