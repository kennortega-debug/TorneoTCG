package com.example.TorneoTCG.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {
}

