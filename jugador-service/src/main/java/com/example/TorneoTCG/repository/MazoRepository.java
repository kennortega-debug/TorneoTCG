package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.Mazo;

@Repository
public interface MazoRepository extends JpaRepository<Mazo, Long> {
}
