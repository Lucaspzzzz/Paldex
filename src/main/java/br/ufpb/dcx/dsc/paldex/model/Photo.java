package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tb_photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "url")
    private String photoURL;

    @OneToOne(mappedBy = "photo")
    private User user;

    public Photo(String photoURL) {
        this.photoURL = photoURL;
    }
}
