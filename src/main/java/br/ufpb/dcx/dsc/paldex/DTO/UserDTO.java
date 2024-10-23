package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.Photo;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.validation.CapitalizedWords;
import br.ufpb.dcx.dsc.paldex.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;

    @NotBlank
    @Unique(fieldName = "username", domainClass = User.class)
    private String username;

    @NotBlank
    @CapitalizedWords
    private String name;

    @Email
    @NotBlank
    @Unique(fieldName = "email", domainClass = User.class)
    private String email;

    @NotBlank
    private String password;

    private Photo photo;

    private Set<TeamDTO> teams;
}
