package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.ComunaDTO;
import com.example.TorneoTCG.dto.RecintoDTO;
import com.example.TorneoTCG.dto.RegionDTO;
import com.example.TorneoTCG.model.Comuna;
import com.example.TorneoTCG.model.Recinto;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.repository.RecintoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class RecintoService {

    @Autowired
    private  RecintoRepository recintoRepository;

    public List<RecintoDTO> obtenerTodos() {
        List<RecintoDTO> listaRecintos = new ArrayList<>();
        List<Recinto> recintos = recintoRepository.findAll();
        for (Recinto r : recintos) {
            listaRecintos.add(convertirADTO(r));
        }
        return listaRecintos;
    }

    public RecintoDTO obtenerPorId(Long id) {
        Recinto r = recintoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recinto no encontrado"));
        return convertirADTO(r);
    }

    public RecintoDTO Guardar(Recinto nuevoRecinto) {
        Recinto recintoGuardado= recintoRepository.save(nuevoRecinto);
        return convertirADTO(recintoGuardado);
    }

    public RecintoDTO actualizar(Long id, RecintoDTO recintoDTO) {
        try {
            return recintoRepository.findById(id)
                .map(RecintoExistente -> {

                    RecintoExistente.setNombre(recintoDTO.getNombre());
                    RecintoExistente.setCapacidad(recintoDTO.getCapacidad());
                    RecintoExistente.setDireccion(recintoDTO.getDireccion());

                    Recinto saved = recintoRepository.save(RecintoExistente);

                    return convertirADTO(saved);

                }).orElseThrow(() -> new RuntimeException("Recinto no encontrado"));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el recinto: " + e.getMessage());
        }
    }
    public String eliminar(Long id) {
        try {
            Recinto R = recintoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(" El recinto no existe"  ));
            recintoRepository.delete(R);
            return "El recinto "+ R.getNombre() +"ha sido eliminado exitosamente";
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al eliminar el recinto: " + e.getMessage());
        }
    }

    private RecintoDTO convertirADTO(Recinto recinto) {
        RecintoDTO DTO = new RecintoDTO();
        DTO.setId(recinto.getId());
        DTO.setNombre(recinto.getNombre());
        DTO.setCapacidad(recinto.getCapacidad());
        DTO.setDireccion(recinto.getDireccion());
        DTO.setComuna(convertirComunaADTO(recinto.getComuna()));
        return DTO;
    }

    private ComunaDTO convertirComunaADTO(Comuna comuna) {
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
