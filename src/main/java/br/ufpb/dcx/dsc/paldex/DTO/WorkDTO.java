package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.Photo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkDTO {
    private Long workId;

    private String type;

    private int level;

    private Photo photo;
}
