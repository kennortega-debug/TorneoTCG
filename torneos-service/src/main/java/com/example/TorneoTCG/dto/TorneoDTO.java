package com.example.TorneoTCG.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TorneoDTO {

    private Long id;

    private String nombre;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String estado;

    private Long idRecinto;
}