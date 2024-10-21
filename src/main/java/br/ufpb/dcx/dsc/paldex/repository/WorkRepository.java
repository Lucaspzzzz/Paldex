package br.ufpb.dcx.dsc.paldex.repository;

import br.ufpb.dcx.dsc.paldex.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
}
