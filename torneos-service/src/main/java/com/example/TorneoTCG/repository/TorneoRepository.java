package com.example.TorneoTCG.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TorneoTCG.model.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    List<Torneo> findByEstadoIgnoreCase(String estado);

}
