package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_active_skill")
public class ActiveSkill extends Skill {

    @Column(name = "power")
    private int power;

    @Column(name = "cooldown")
    private int cooldown;

    @Column(name = "level")
    private int level;

    @Enumerated(EnumType.STRING)
    @Column(name = "element")
    private Elements element;

    @Column(name = "range")
    private int range;
}
