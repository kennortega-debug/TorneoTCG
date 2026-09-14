package com.example.TorneoTCG.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.model.Ronda;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByRonda(Ronda ronda);
}