package com.example.TorneoTCG.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TorneoTCG.model.Ronda;
import com.example.TorneoTCG.model.Torneo;

public interface RondaRepository extends JpaRepository<Ronda, Long> {

    List<Ronda> findByTorneo(Torneo torneo);
}