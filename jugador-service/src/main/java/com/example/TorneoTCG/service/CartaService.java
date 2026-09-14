package com.example.TorneoTCG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TorneoTCG.dto.CartaDTO;
import com.example.TorneoTCG.model.Carta;
import com.example.TorneoTCG.repository.CartaRepository;

import java.util.ArrayList; 
import java.util.List;

@Service
public class CartaService {

    @Autowired
    private CartaRepository cartaRepository;

    public CartaDTO buscarPorId(Long id) {
        Carta carta = cartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carta no encontrada"));
        return convertirADTO(carta);
    }

    public CartaDTO guardarCarta(CartaDTO cartaDTO) {
        Carta carta = new Carta();
        carta.setNombre(cartaDTO.getNombre());
        carta.setDescripcion(cartaDTO.getDescripcion());
        carta.setRareza(cartaDTO.getRareza());
        carta.setCosto(cartaDTO.getCosto());
        
        Carta cartaGuardada = cartaRepository.save(carta);
        
        return convertirADTO(cartaGuardada);
    }

    public List<CartaDTO> obtenerTodas() {
        return cartaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList(); // ¡Cambiado a .toList() limpio!
    }

    private CartaDTO convertirADTO(Carta carta) {
        if (carta == null) return null;

        CartaDTO dto = new CartaDTO();
        dto.setId(carta.getId());
        dto.setNombre(carta.getNombre());
        dto.setDescripcion(carta.getDescripcion());
        dto.setRareza(carta.getRareza());
        dto.setCosto(carta.getCosto());
        
        try {
            if (carta.getCartaMazos() != null && !carta.getCartaMazos().isEmpty()) {
                List<String> nombres = carta.getCartaMazos().stream()
                        .map(cm -> cm.getMazo().getNombre()) 
                        .toList(); // ¡Cambiado aquí también!
                dto.setNombresMazos(nombres);
            } else {
                dto.setNombresMazos(new ArrayList<>()); 
            }
        } catch (Exception e) {
            dto.setNombresMazos(new ArrayList<>());
        }
        
        return dto;
    }

    public String eliminar(Long id) {
        if (!cartaRepository.existsById(id)) {
            return "Carta no encontrada";
        }
        cartaRepository.deleteById(id);
        return "Carta eliminada exitosamente";
    }
}

