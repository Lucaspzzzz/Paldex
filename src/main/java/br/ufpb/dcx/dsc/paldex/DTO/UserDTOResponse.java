package br.ufpb.dcx.dsc.paldex.DTO;

import br.ufpb.dcx.dsc.paldex.model.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class UserDTOResponse {
    private Long userId;
    private String name;
    private String email;
    private String username;
    private Photo photo;
    List<Long> timesIds;
}
