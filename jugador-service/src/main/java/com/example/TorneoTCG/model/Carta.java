package com.example.TorneoTCG.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carta")
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carta")
    private Long id;

    @NotBlank(message = "El nombre de la carta es obligatorio")
    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(length = 50)
    private String descripcion;

    @Column(length = 20)
    private String rareza;

    @Min(value = 0, message = "El costo no puede ser negativo")
    private Integer costo;

    @OneToMany(mappedBy = "carta")
    @ToString.Exclude
    private List<CartaMazo> cartaMazos;
}
