package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.RegionDTO;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class RegionService {
    
    @Autowired
    private RegionRepository regionRepository;

    public List<RegionDTO> obtenerTodos() {
        List<RegionDTO> listaRegiones = new ArrayList<>();
        List<Region> regiones = regionRepository.findAll();
        for (Region r : regiones) {
            listaRegiones.add(convertirADTO(r));
        }
        return listaRegiones;
    }

    public RegionDTO obtenerPorId(Long id) {
        Region r = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada"));
        return convertirADTO(r);
    }

    public RegionDTO Guardar(Region nuevaRegion) {
        Region regionGuardada= regionRepository.save(nuevaRegion);
        return convertirADTO(regionGuardada);
    }

    public RegionDTO actualizar(Long id, RegionDTO regionDTO) {
        try {
            return regionRepository.findById(id)
                .map(regionExistente -> {

                    regionExistente.setNombre(regionDTO.getNombre());

                    Region saved = regionRepository.save(regionExistente);

                    return convertirADTO(saved);

                }).orElseThrow(() -> new RuntimeException("Región no encontrada"));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar la región: " + e.getMessage());
        }
    }
    public String eliminar(Long id) {
        try {
            Region r = regionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(" La región no existe"  ));
            regionRepository.delete(r);
            return "La región "+ r.getNombre() +"ha sido eliminada exitosamente";
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al eliminar la región: " + e.getMessage());
        }
    }

    private RegionDTO convertirADTO(Region region) {
        RegionDTO DTO = new RegionDTO();
        DTO.setId(region.getId());
        DTO.setNombre(region.getNombre());
        return DTO;
    }

}
