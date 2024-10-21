package br.ufpb.dcx.dsc.paldex.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticDTO {

    private Long id;

    private int hp;

    private int defense;

    private int creationSpeed;

    private int bodyAttack;

    private int distanceAttack;

    private int price;

    private int stamina;

    private int support;

    private int raceSpeed;

    private int mountedSprintSpeed;

    private int slowWalkingSpeed;
}
