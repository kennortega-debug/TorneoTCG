package com.example.TorneoTCG.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.MazoDTO;
import com.example.TorneoTCG.model.Mazo;
import com.example.TorneoTCG.model.Jugador; 
import com.example.TorneoTCG.repository.MazoRepository;
import com.example.TorneoTCG.repository.JugadorRepository; 

import jakarta.transaction.Transactional;
@Service
@Transactional
public class MazoService {

    @Autowired
    private MazoRepository mazoRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<MazoDTO> obtenerTodos() {
        return mazoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public MazoDTO buscarPorId(Long id) {
        Mazo mazo = mazoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Mazo no encontrado!"));

        return convertirADTO(mazo);
    }

    public Mazo guardar(Mazo mazo) {
        return mazoRepository.save(mazo);
    }

    public String asignarDueñoAMazo(Long mazoId, Integer jugadorId) {
        Mazo mazo = mazoRepository.findById(mazoId)
                .orElseThrow(() -> new RuntimeException("El mazo no existe"));

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("El jugador no existe"));

        mazo.setJugador(jugador);
        mazoRepository.save(mazo);

        return "Mazo asignado correctamente";
    }

    public String eliminar(Long id) {
        if (!mazoRepository.existsById(id)) {
            return "Mazo no encontrado";
        }

        mazoRepository.deleteById(id);
        return "Mazo eliminado exitosamente";
    }

    private MazoDTO convertirADTO(Mazo mazo) {
        if (mazo == null) return null;

        MazoDTO dto = new MazoDTO();
        dto.setId(mazo.getId());
        dto.setNombre(mazo.getNombre());
        dto.setDescripcion(mazo.getDescripcion());
        
        try {
            if (mazo.getJugador() != null) {
                dto.setIdJugador(mazo.getJugador().getId());
                dto.setNombreJugador(mazo.getJugador().getNombre());
            } else {
                dto.setNombreJugador("Busca dueño");
            }
        } catch (Exception e) {
            
            dto.setNombreJugador("Error al cargar dueño (Desconocido)");
        }

        return dto;
    }
}