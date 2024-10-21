package br.ufpb.dcx.dsc.paldex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;

@Data
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

    @JsonIgnore
    @ManyToMany(mappedBy = "drops")
    private Collection<Pal> pals;
}
