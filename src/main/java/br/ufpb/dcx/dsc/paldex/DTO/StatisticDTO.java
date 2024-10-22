package br.ufpb.dcx.dsc.paldex.DTO;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticDTO {

    private Long id;

    @Min(value = 1, message = "HP deve ser no mínimo 1")
    private int hp;

    @Min(value = 0, message = "Defesa deve ser no mínimo 0")
    private int defense;

    @Min(value = 1, message = "Velocidade de criação deve ser no mínimo 1")
    private int creationSpeed;

    @Min(value = 0, message = "Ataque corporal deve ser no mínimo 0")
    private int bodyAttack;

    @Min(value = 0, message = "Ataque à distância deve ser no mínimo 0")
    private int distanceAttack;

    @Min(value = 1, message = "Preço deve ser no mínimo 1")
    private int price;

    @Min(value = 0, message = "Estamina deve ser no mínimo 0")
    private int stamina;

    @Min(value = 0, message = "Suporte deve ser no mínimo 0")
    private int support;

    @Min(value = 1, message = "Velocidade de corrida deve ser no mínimo 1")
    private int raceSpeed;

    @Min(value = 1, message = "Velocidade de sprint montado deve ser no mínimo 1")
    private int mountedSprintSpeed;

    @Min(value = 1, message = "Velocidade de caminhada lenta deve ser no mínimo 1")
    private int slowWalkingSpeed;
}
