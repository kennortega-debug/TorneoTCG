package com.example.TorneoTCG.dto;

import lombok.Data;

@Data
public class RankingDTO {
    private Integer idJugador;
    private String nombreJugador;
    private Integer puntajeTotal;
    private Integer partidasJugadas;
    private Integer victorias;
}
