package br.ufpb.dcx.dsc.paldex.repository;

import br.ufpb.dcx.dsc.paldex.model.Role;
import br.ufpb.dcx.dsc.paldex.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
