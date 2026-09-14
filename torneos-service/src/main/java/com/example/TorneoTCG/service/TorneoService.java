package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.client.RecintoClient;
import com.example.TorneoTCG.dto.TorneoDTO;
import com.example.TorneoTCG.dtoexterno.RecintoExternoDTO;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.repository.TorneoRepository;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private RecintoClient recintoClient;

    public List<TorneoDTO> obtenerTodos() {

        List<TorneoDTO> listaDTO = new ArrayList<>();

        List<Torneo> torneos = torneoRepository.findAll();

        for (Torneo torneo : torneos) {
            listaDTO.add(convertirADTO(torneo));
        }

        return listaDTO;
    }

    public TorneoDTO buscarPorId(Long id) {

        Torneo torneo = torneoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Torneo no encontrado"));

        return convertirADTO(torneo);
    }

    public TorneoDTO guardar(Torneo torneo) {

        validarRecinto(torneo.getIdRecinto());

        Torneo guardado = torneoRepository.save(torneo);

        return convertirADTO(guardado);
    }

    public TorneoDTO actualizar(Long id, Torneo torneo) {
    Torneo existente = torneoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));
    existente.setNombre(torneo.getNombre());
    existente.setFechaInicio(torneo.getFechaInicio());
    existente.setFechaFin(torneo.getFechaFin());
    existente.setEstado(torneo.getEstado());

    validarRecinto(torneo.getIdRecinto());
    existente.setIdRecinto(torneo.getIdRecinto());

    Torneo actualizado = torneoRepository.save(existente);
    return convertirADTO(actualizado);
}

public String eliminar(Long id) {
    if (torneoRepository.existsById(id)) {
        torneoRepository.deleteById(id);
        return "Torneo eliminado correctamente";
    } else {
        return "Torneo no encontrado";
    }
}

    private void validarRecinto(Long idRecinto) {

        RecintoExternoDTO recinto = recintoClient.obtenerRecinto(idRecinto);

        if (recinto == null) {
            throw new RuntimeException("Recinto no encontrado");
        }
    }

    private TorneoDTO convertirADTO(Torneo torneo) {

        TorneoDTO dto = new TorneoDTO();

        dto.setId(torneo.getId());
        dto.setNombre(torneo.getNombre());
        dto.setFechaInicio(torneo.getFechaInicio());
        dto.setFechaFin(torneo.getFechaFin());
        dto.setEstado(torneo.getEstado());
        dto.setIdRecinto(torneo.getIdRecinto());

        return dto;
    }
}
