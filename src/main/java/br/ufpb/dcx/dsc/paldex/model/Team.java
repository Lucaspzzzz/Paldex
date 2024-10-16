package br.ufpb.dcx.dsc.paldex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tb_team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long teamId;
    @Column(name = "nome")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
//    @JsonIgnore
//    Collection<Pal> palList;


}
