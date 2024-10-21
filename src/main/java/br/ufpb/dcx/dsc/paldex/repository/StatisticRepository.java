package br.ufpb.dcx.dsc.paldex.repository;

import br.ufpb.dcx.dsc.paldex.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
}
