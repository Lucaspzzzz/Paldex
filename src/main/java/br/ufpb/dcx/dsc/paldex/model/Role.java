package br.ufpb.dcx.dsc.paldex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tb_roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;
}
