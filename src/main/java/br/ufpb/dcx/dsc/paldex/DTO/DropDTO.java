package br.ufpb.dcx.dsc.paldex.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DropDTO {

    private Long dropId;

    @NotBlank(message = "O nome do drop não pode estar vazio")
    private String name;

    @NotBlank(message = "A descrição do drop não pode estar vazia")
    private String description;

    @NotNull(message = "A taxa de drop é obrigatória")
    @Min(value = 1, message = "A taxa de drop deve ser no mínimo 1")
    @Max(value = 100, message = "A taxa de drop deve ser no máximo 100")
    private Integer rate;

    @NotNull(message = "A quantidade de drop é obrigatória")
    @Min(value = 1, message = "A quantidade de drop deve ser no mínimo 1")
    private Integer amount;
}