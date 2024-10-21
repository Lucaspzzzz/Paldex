package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_drop")
public class Drop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "drop_id")
    private Long dropId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rate")
    private int rate;

    @Column(name = "amount")
    private int amount;

    @ManyToMany(mappedBy = "drops")
    private Set<Pal> pals;
}
