package com.example.TorneoTCG.dto;

import lombok.Data;

@Data
public class OrganizadorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String cargo;
}
