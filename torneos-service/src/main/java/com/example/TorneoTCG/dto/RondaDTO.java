package com.example.TorneoTCG.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RondaDTO {

    private Long id;

    private Integer numeroRonda;

    private LocalDate fecha;

    private Long idTorneo;
}