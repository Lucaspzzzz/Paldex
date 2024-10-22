package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.Photo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkDTO {
    private Long workId;

    @NotNull(message = "O tipo de trabalho não pode estar vazio")
    private String type;

    @Min(value = 1, message = "O nível deve ser no mínimo 1")
    private int level;

    private Photo photo;
}
