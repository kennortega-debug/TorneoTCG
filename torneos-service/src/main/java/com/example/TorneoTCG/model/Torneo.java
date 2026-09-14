package com.example.TorneoTCG.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "torneo")
public class Torneo {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_torneo")
   private Long id;

   @NotBlank(message = "El Torneo debe tener nombre")
   @Size(min = 3, max = 50)
   private String nombre;

   @NotNull(message =  "Torneo debe tener fecha de inicio")
   @Column(name = "fecha_inicio")
   private LocalDate fechaInicio;

   @NotNull(message =  "Torneo debe tener fecha de fin")
   @Column(name = "fecha_fin")
   private LocalDate fechaFin;

   @NotBlank(message = "El estado de torneo es obligatorio")
   private String estado;

   @NotNull(message = "El id de recinto es obligatorio")
   @Min(1)
   @Column(name = "id_recinto")
   private Long idRecinto;
}

