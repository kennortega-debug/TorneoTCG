package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.client.JugadorClient;
import com.example.TorneoTCG.dto.ParticipacionDTO;
import com.example.TorneoTCG.dtoexterno.JugadorExternoDTO;
import com.example.TorneoTCG.model.Participacion;
import com.example.TorneoTCG.repository.ParticipacionRepository;

@Service
public class ParticipacionService {

    @Autowired
    private ParticipacionRepository participacionRepository;

    @Autowired
    private JugadorClient jugadorClient;

    public List<ParticipacionDTO> obtenerTodos() {

        List<ParticipacionDTO> listaDTO = new ArrayList<>();

        List<Participacion> participaciones =
                participacionRepository.findAll();

        for (Participacion participacion : participaciones) {
            listaDTO.add(convertirADTO(participacion));
        }

        return listaDTO;
    }

    public ParticipacionDTO buscarPorId(Long id) {

        Participacion participacion =
                participacionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Participación no encontrada"));

        return convertirADTO(participacion);
    }

    public ParticipacionDTO guardar(Participacion participacion) {

        validarJugador(participacion.getIdJugador());

        Participacion guardada =
                participacionRepository.save(participacion);

        return convertirADTO(guardada);
    }

    public ParticipacionDTO actualizar(Long id, Participacion participacion) {
    Participacion existente = participacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Participación no encontrada"));
    existente.setIdJugador(participacion.getIdJugador());
    existente.setTorneo(participacion.getTorneo());
    existente.setRondaInscripcion(participacion.getRondaInscripcion());
    Participacion actualizada = participacionRepository.save(existente);
    return convertirADTO(actualizada);
}

    public String eliminar(Long id) {
    if (participacionRepository.existsById(id)) {
        participacionRepository.deleteById(id);
        return "Participación eliminada correctamente";
    } else {
        return "Participación no encontrada";
    }
}

    private void validarJugador(Integer idJugador) {

        JugadorExternoDTO jugador = jugadorClient.obtenerJugador(idJugador);

        if (jugador == null) {
            throw new RuntimeException("Jugador no encontrado");
        }
    }

    private ParticipacionDTO convertirADTO(
            Participacion participacion) {

        ParticipacionDTO dto = new ParticipacionDTO();

        dto.setId(participacion.getId());

        dto.setIdJugador(
                participacion.getIdJugador()
        );

        dto.setIdTorneo(
                participacion.getTorneo().getId()
        );

        dto.setRondaInscripcion(
                participacion.getRondaInscripcion()
        );

        return dto;
    }
}