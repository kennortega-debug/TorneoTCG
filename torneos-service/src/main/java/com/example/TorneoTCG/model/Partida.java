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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partida")
public class Partida {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_partida")
   private Long id;

   @NotBlank(message = "la mesa es obligatoria")
   @Size(min = 1, max = 10)
   private String mesa;

   @NotBlank(message = "El estado es obligatorio")
   private String estado;

   @NotNull(message = "La ronda es obligatoria")
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_ronda", nullable = false)
   private Ronda ronda;

   @NotNull(message = "La cantidad de jugadores es obligatoria")
   @Min(value = 3, message = "La partida debe tener mínimo 3 jugadores")
   @Max(value = 5, message = "La partida permite máximo 5 jugadores")
   @Column(name = "cantidad_jugadores", nullable = false)
   private Integer cantidadJugadores;

   @NotNull(message = "Jugador 1 obligatorio")
   @Column(name = "id_jugador1", nullable = false)
   private Integer idJugador1;

   @NotNull(message = "Jugador 2 obligatorio")
   @Column(name = "id_jugador2", nullable = false)
   private Integer idJugador2;

   @NotNull(message = "Jugador 3 obligatorio")
   @Column(name = "id_jugador3", nullable = false)
   private Integer idJugador3;

   @Column(name = "id_jugador4")
   private Integer idJugador4;

   @Column(name = "id_jugador5")
   private Integer idJugador5;
}
