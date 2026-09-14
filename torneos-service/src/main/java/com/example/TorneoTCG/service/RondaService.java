package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.RondaDTO;
import com.example.TorneoTCG.model.Ronda;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.repository.RondaRepository;
import com.example.TorneoTCG.repository.TorneoRepository;

@Service
public class RondaService {

    @Autowired
    private RondaRepository rondaRepository;

    @Autowired
    private TorneoRepository torneoRepository;

    public List<RondaDTO> obtenerTodos() {

        List<RondaDTO> listaDTO = new ArrayList<>();

        List<Ronda> rondas = rondaRepository.findAll();

        for (Ronda ronda : rondas) {
            listaDTO.add(convertirADTO(ronda));
        }

        return listaDTO;
    }

    public List<RondaDTO> obtenerPorTorneo(Long idTorneo) {

        Torneo torneo = torneoRepository.findById(idTorneo)
                .orElseThrow(() ->
                        new RuntimeException("Torneo no encontrado"));

        List<RondaDTO> listaDTO = new ArrayList<>();

        List<Ronda> rondas = rondaRepository.findByTorneo(torneo);

        for (Ronda ronda : rondas) {
            listaDTO.add(convertirADTO(ronda));
        }

        return listaDTO;
    }

    public RondaDTO buscarPorId(Long id) {

        Ronda ronda = rondaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ronda no encontrada"));

        return convertirADTO(ronda);
    }

    public RondaDTO guardar(Ronda ronda) {

        Ronda guardada = rondaRepository.save(ronda);

        return convertirADTO(guardada);
    }

    public RondaDTO actualizar(Long id, Ronda ronda) {
    Ronda existente = rondaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ronda no encontrada"));
    existente.setNumeroRonda(ronda.getNumeroRonda());
    existente.setFecha(ronda.getFecha());
    existente.setTorneo(ronda.getTorneo());
    Ronda actualizada = rondaRepository.save(existente);
    return convertirADTO(actualizada);
}

    public String eliminar(Long id) {
    if (rondaRepository.existsById(id)) {
        rondaRepository.deleteById(id);
        return "Ronda eliminada correctamente";
    } else {
        return "Ronda no encontrada";
    }
}

    private RondaDTO convertirADTO(Ronda ronda) {

        RondaDTO dto = new RondaDTO();

        dto.setId(ronda.getId());
        dto.setNumeroRonda(ronda.getNumeroRonda());
        dto.setFecha(ronda.getFecha());

        dto.setIdTorneo(
                ronda.getTorneo().getId()
        );

        return dto;
    }
}