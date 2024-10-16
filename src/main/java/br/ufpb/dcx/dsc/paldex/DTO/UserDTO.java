package br.ufpb.dcx.dsc.paldex.DTO;


import br.ufpb.dcx.dsc.paldex.model.Photo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private Photo photo;
    private String username;
    private String password;
}
