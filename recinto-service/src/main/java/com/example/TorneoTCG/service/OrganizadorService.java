package com.example.TorneoTCG.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.OrganizadorDTO;
import com.example.TorneoTCG.model.Organizador;
import com.example.TorneoTCG.repository.OrganizadorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrganizadorService {

    @Autowired
    private OrganizadorRepository organizadorRepository;

    public List<OrganizadorDTO> obtenerTodos() {
        List<OrganizadorDTO> listaOrganizadores = new ArrayList<>();
        List<Organizador> organizadores = organizadorRepository.findAll();
        for (Organizador o : organizadores) {
            listaOrganizadores.add(convertirADTO(o));
        }
        return listaOrganizadores;
    }

    public OrganizadorDTO obtenerPorId(Long id) {
        Organizador o = organizadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
        return convertirADTO(o);
    }

    public OrganizadorDTO Guardar(Organizador nuevoOrganizador) {
        Organizador organizadorGuardado= organizadorRepository.save(nuevoOrganizador);
        return convertirADTO(organizadorGuardado);
    }

    public OrganizadorDTO actualizar(Long id, OrganizadorDTO organizadorDTO) {
        try {
            return organizadorRepository.findById(id)
                .map(organizadorExistente -> {

                    organizadorExistente.setNombre(organizadorDTO.getNombre());
                    organizadorExistente.setEmail(organizadorDTO.getEmail());
                    organizadorExistente.setTelefono(organizadorDTO.getTelefono());
                    organizadorExistente.setApellido(organizadorDTO.getApellido());
                    organizadorExistente.setCargo(organizadorDTO.getCargo());

                    Organizador saved = organizadorRepository.save(organizadorExistente);

                    return convertirADTO(saved);

                }).orElseThrow(() -> new RuntimeException("Organizador no encontrado"));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al actualizar el organizador: " + e.getMessage());
        }
    }

    private OrganizadorDTO convertirADTO(Organizador organizador) {
        OrganizadorDTO DTO = new OrganizadorDTO();
        DTO.setId(organizador.getId());
        DTO.setNombre(organizador.getNombre());
        DTO.setApellido(organizador.getApellido());
        DTO.setEmail(organizador.getEmail());
        DTO.setTelefono(organizador.getTelefono());
        DTO.setCargo(organizador.getCargo());
        return DTO;
    }

}
