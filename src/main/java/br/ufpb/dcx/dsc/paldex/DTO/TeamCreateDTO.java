package br.ufpb.dcx.dsc.paldex.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateDTO {

    @NotBlank(message = "O nome do time é obrigatório.")
    private String name;
}
