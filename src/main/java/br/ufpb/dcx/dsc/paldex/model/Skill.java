package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}