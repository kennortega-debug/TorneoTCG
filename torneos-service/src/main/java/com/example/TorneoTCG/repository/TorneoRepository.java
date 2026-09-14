package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TorneoTCG.model.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

}