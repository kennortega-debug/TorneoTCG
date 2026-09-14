package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.Recinto;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Long> {
}
