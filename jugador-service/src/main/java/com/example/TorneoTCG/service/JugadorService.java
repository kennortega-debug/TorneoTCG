package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.JugadorDTO;
import com.example.TorneoTCG.model.Jugador;
import com.example.TorneoTCG.repository.JugadorRepository;
import com.example.TorneoTCG.model.Mazo;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;

    public List<JugadorDTO> obtenerTodos() {
        return jugadorRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public JugadorDTO buscarPorId(Integer id) {
        Jugador jugador = jugadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡El duelista no existe en nuestros registros!"));
        return convertirADTO(jugador);
    }

    public String eliminar(Integer id) {
        try {
            Jugador jugador = jugadorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(" El jugador con ID " + id + " no existe."));
            jugadorRepository.delete(jugador);
            return "El duelista '" + jugador.getNombre() + "' ha sido descalificado del torneo.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Jugador guardar(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    private JugadorDTO convertirADTO(Jugador jugador) {
        if (jugador == null) return null;

        JugadorDTO dto = new JugadorDTO();
        dto.setId(jugador.getId());
        dto.setNombre(jugador.getNombre());
        dto.setEmail(jugador.getEmail());
        try {
            //  mapear los nombres de los mazos del jugador
            if (jugador.getMazos() != null && !jugador.getMazos().isEmpty()) {
                dto.setNombreMazos(
                    jugador.getMazos()
                        .stream()
                        .map(Mazo::getNombre)
                        .toList());
            } else {
                dto.setNombreMazos(new ArrayList<>());
            }
        } catch (Exception e) {
            // Si algo falla leyendo los mazos de la base de datos aseguramos que la lista no rompa el programa mandándola vacía.
            dto.setNombreMazos(new ArrayList<>());
        }
        return dto;
    }
}