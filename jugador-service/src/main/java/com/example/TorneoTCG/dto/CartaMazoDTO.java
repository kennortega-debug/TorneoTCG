package com.example.TorneoTCG.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaMazoDTO {
    private Long id;
    private Integer cantidad;
    private String nombreMazo;  
    private String nombreCarta; 
}
