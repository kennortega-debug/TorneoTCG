package com.example.TorneoTCG.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.TorneoTCG.dto.CartaDTO;
import com.example.TorneoTCG.service.CartaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/cartas")
public class CartaController {

    @Autowired
    private CartaService cartaService;

    @GetMapping
    public ResponseEntity<List<CartaDTO>> todasLasCartas() {
        List<CartaDTO> cartas = cartaService.obtenerTodas();
        if (cartas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaDTO> buscarPorId(@PathVariable Long id) {
        try {
            CartaDTO carta = cartaService.buscarPorId(id);
            return new ResponseEntity<>(carta, HttpStatus.OK);
        } catch (RuntimeException e) {
            
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CartaDTO> agregarCarta(@Valid @RequestBody CartaDTO cartaDTO) {
        try {
            CartaDTO guardada = cartaService.guardarCarta(cartaDTO);
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCarta(@PathVariable Long id) {
        String resultado = cartaService.eliminar(id);
        
        
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

}



