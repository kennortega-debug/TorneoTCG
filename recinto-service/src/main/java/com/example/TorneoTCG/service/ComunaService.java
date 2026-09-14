package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.ComunaDTO;
import com.example.TorneoTCG.dto.RegionDTO;
import com.example.TorneoTCG.model.Comuna;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.repository.ComunaRepository;
import com.example.TorneoTCG.repository.RegionRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class ComunaService {

    @Autowired
    private  ComunaRepository comunaRepository;

    @Autowired
    private  RegionRepository regionRepository;

    public List<ComunaDTO> obtenerTodos() {
        List<ComunaDTO> listaComunas = new ArrayList<>();
        List<Comuna> comunas = comunaRepository.findAll();
        for (Comuna c : comunas) {
            listaComunas.add(convertirADTO(c));
        }
        return listaComunas;
    }

    public ComunaDTO obtenerPorId(Long id) {
        Comuna c = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
        return convertirADTO(c);
    }

    public ComunaDTO Guardar(Comuna nuevaComuna) {
        Comuna comunaGuardada= comunaRepository.save(nuevaComuna);
        return convertirADTO(comunaGuardada);
    }

    public ComunaDTO actualizar(Long id, ComunaDTO comunaDTO) {
        try {
            return comunaRepository.findById(id)
                .map(comunaExistente -> {

                    comunaExistente.setNombre(comunaDTO.getNombre());
                    Region region = regionRepository.findById(comunaDTO.getRegion().getId())
                            .orElseThrow(() -> new RuntimeException("Región no encontrada"));
                    comunaExistente.setRegion(region);

                    Comuna saved = comunaRepository.save(comunaExistente);

                    return convertirADTO(saved);

                }).orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar la comuna: " + e.getMessage());
        }
    }
    public String eliminar(Long id) {
        try {
            Comuna C = comunaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(" La comuna no existe"  ));
            comunaRepository.delete(C);
            return "La comuna "+ C.getNombre() +"ha sido eliminada exitosamente";
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al eliminar la comuna: " + e.getMessage());
        }
    }

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO DTO = new ComunaDTO();
        DTO.setId(comuna.getId());
        DTO.setNombre(comuna.getNombre());
        DTO.setRegion(convertirRegionADTO(comuna.getRegion()));
        return DTO;
    }

    private RegionDTO convertirRegionADTO(Region region) {
        RegionDTO DTO = new RegionDTO();
        DTO.setId(region.getId());
        DTO.setNombre(region.getNombre());
        return DTO;
    }
}
