package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.validation.CapitalizedWords;
import br.ufpb.dcx.dsc.paldex.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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
}

