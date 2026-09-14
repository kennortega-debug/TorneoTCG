package com.example.TorneoTCG.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MazoDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private Integer idJugador;

    private String nombreJugador;

    private List<String> nombresCartas;
}
