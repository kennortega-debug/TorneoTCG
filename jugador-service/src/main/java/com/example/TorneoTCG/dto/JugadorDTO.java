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

public class JugadorDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private List<String> nombreMazos;
    
}


