package com.example.TorneoTCG.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TorneoTCG.model.CartaMazo;

@Repository
public interface CartaMazoRepository extends JpaRepository<CartaMazo, Long> {
}
