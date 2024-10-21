package br.ufpb.dcx.dsc.paldex.DTO;

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
    private String username ;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

