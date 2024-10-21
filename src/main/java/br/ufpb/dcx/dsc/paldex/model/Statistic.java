package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_statistic")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "hp")
    private int hp;

    @Column(name = "defense")
    private int defense;

    @Column(name = "creation_speed")
    private int creationSpeed;

    @Column(name = "body_attack")
    private int bodyAttack;

    @Column(name = "distance_attack")
    private int distanceAttack;

    @Column(name = "price")
    private int price;

    @Column(name = "stamina")
    private int stamina;

    @Column(name = "support")
    private int support;

    @Column(name = "race_speed")
    private int raceSpeed;

    @Column(name = "mounted_sprint_speed")
    private int mountedSprintSpeed;

    @Column(name = "slow_walking_speed")
    private int slowWalkingSpeed;
}
