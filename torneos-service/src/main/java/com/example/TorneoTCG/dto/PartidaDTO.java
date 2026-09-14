package com.example.TorneoTCG.dto;

import lombok.Data;

@Data
public class PartidaDTO {

    private Long id;

    private String mesa;

    private String estado;

    private Integer cantidadJugadores;

    private Long idRonda;

    private Integer idJugador1;

    private Integer idJugador2;

    private Integer idJugador3;

    private Integer idJugador4;

    private Integer idJugador5;
}