package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.Carta;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {
}
