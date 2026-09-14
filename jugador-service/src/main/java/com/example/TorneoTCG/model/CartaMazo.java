package com.example.TorneoTCG.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carta_mazo")
public class CartaMazo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carta_mazo")
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_mazo", nullable = false)
    private Mazo mazo;

    @ManyToOne
    @JoinColumn(name = "id_carta", nullable = false)
    private Carta carta;

    
}

