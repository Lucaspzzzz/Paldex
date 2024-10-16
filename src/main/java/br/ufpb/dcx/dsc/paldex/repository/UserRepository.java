package br.ufpb.dcx.dsc.paldex.repository;

import br.ufpb.dcx.dsc.paldex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
