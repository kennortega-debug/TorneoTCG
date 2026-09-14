package com.example.TorneoTCG.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mazo")
public class Mazo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mazo")
    private Long id;

    @NotBlank(message = "El nombre del mazo es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre del mazo debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(length = 50)
    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(length = 10)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @OneToMany(mappedBy = "mazo")
    @ToString.Exclude
    private List<CartaMazo> cartaMazos;
    
}
