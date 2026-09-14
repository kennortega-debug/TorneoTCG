package com.example.TorneoTCG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
