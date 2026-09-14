package com.example.TorneoTCG.dto;

import lombok.Data;

@Data
public class ParticipacionDTO {
    private Long id;
    private Integer idJugador;
    private Long idTorneo;
    private Integer rondaInscripcion;
}
