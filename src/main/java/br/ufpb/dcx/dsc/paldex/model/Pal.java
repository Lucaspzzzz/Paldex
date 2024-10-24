package br.ufpb.dcx.dsc.paldex.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_pal")
@ToString(exclude = {"teams"})
@NoArgsConstructor
@AllArgsConstructor
public class Pal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pal_id")
    private Long palId;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "rarity")
    private String rarity;

    @ElementCollection(targetClass = Elements.class)
    @CollectionTable(name = "pal_elements", joinColumns = @JoinColumn(name = "pal_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "element")
    private Set<Elements> elements;

    @ManyToMany(mappedBy = "pals", fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private Set<Team> teams;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "pal_drops",
            joinColumns = @JoinColumn(name = "pal_id"),
            inverseJoinColumns = @JoinColumn(name = "drop_id")
    )
    private Set<Drop> drops;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistic_id", referencedColumnName = "id")
    private Statistic statistic;

    @ManyToOne
    @JoinColumn(name = "partner_skill_id")
    private ActiveSkill partnerSkill;

    @ManyToOne
    @JoinColumn(name = "passive_skill_id")
    private Skill passiveSkill;

    @ManyToOne
    @JoinColumn(name = "active_skill_id")
    private ActiveSkill activeSkill;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pal_id")
    private Set<Work> works;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
