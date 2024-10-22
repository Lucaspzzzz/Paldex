package br.ufpb.dcx.dsc.paldex.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditAccountDTO {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    private String email;

    // Getters and Setters
}
