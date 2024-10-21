package br.ufpb.dcx.dsc.paldex.repository;

import br.ufpb.dcx.dsc.paldex.model.Pal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalRepository extends JpaRepository<Pal, Long> {
}
