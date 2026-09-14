package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.Organizador;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {
}
