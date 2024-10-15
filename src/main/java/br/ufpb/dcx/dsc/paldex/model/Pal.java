package br.ufpb.dcx.dsc.paldex.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_pal")
public class Pal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pal_id")
    private Long palId;




}
