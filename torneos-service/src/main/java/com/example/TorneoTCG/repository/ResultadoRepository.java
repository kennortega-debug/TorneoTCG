package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    Resultado findByPartida(Partida partida);
}