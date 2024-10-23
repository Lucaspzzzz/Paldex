package br.ufpb.dcx.dsc.paldex.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {

    private Long photoId;
    private String photoURL;

    public PhotoDTO(String photoURL) {
        this.photoURL = photoURL;
    }
}
