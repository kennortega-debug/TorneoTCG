package com.example.TorneoTCG.model;

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
@Table(name = "resultado")
public class Resultado {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_resultado")
   private Long id;

   @NotNull(message = "La partida es obligatoria")
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_partida", nullable = false, unique = true)
   private Partida partida;

   @NotNull(message = "Debe existir un ganador")
   @Column(name = "id_ganador", nullable = false)
   private Integer idGanador;

   @Column(name = "puntaje_jugador1")
   private Integer puntajeJugador1;

   @Column(name = "puntaje_jugador2")
   private Integer puntajeJugador2;

   @Column(name = "puntaje_jugador3")
   private Integer puntajeJugador3;

   @Column(name = "puntaje_jugador4")
   private Integer puntajeJugador4;

   @Column(name = "puntaje_jugador5")
   private Integer puntajeJugador5;

   @Column(name = "posicion_jugador1")
   private Integer posicionJugador1;

   @Column(name = "posicion_jugador2")
   private Integer posicionJugador2;

   @Column(name = "posicion_jugador3")
   private Integer posicionJugador3;

   @Column(name = "posicion_jugador4")
   private Integer posicionJugador4;

   @Column(name = "posicion_jugador5")
   private Integer posicionJugador5;
}
