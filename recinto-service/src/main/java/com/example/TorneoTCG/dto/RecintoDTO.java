package com.example.TorneoTCG.dto;

import lombok.Data;

@Data
public class RecintoDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Integer capacidad;
    private ComunaDTO comuna;
}
