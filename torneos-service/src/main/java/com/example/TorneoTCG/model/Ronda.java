package com.example.TorneoTCG.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ronda")
public class Ronda {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ronda")
    private Long id;

    @NotNull(message = "El número de ronda es obligatorio")
    @Column(name = "numero_ronda")
    private Integer numeroRonda;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El torneo es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_torneo", nullable = false)
    private Torneo torneo;
}