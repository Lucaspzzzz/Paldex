package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_id")
    private Long workId;

    @Column(name = "type")
    private String type;

    @Column(name = "level")
    private int level;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
