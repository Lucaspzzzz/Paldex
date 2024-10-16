package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class TeamDTO {

    private Long teamId;
    private String name;
    private User user;

}
