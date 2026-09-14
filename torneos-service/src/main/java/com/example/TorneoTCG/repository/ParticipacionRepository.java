package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.TorneoTCG.model.Participacion;
import com.example.TorneoTCG.model.Torneo;

public interface ParticipacionRepository extends JpaRepository<Participacion, Long> {

    List<Participacion> findByTorneo(Torneo torneo);
}