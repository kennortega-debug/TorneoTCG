package com.example.TorneoTCG.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.TorneoTCG.dto.CartaMazoDTO;
import com.example.TorneoTCG.service.CartaMazoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/cartas-mazos")
public class CartaMazoController {

    @Autowired
    private CartaMazoService cartaMazoService;

    @GetMapping
    public ResponseEntity<List<CartaMazoDTO>> listarTodo() {
        List<CartaMazoDTO> lista = cartaMazoService.obtenerTodas();
        return lista.isEmpty() 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
            : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/mazo/{mazoId}/carta/{cartaId}")
    public ResponseEntity<String> agregarCartaAMazo(
            @Valid
            @PathVariable Long mazoId, 
            @PathVariable Long cartaId, 
            @RequestParam(defaultValue = "1") Integer cantidad) {
        try {
            String resultado = cartaMazoService.agregarCartaAMazo(mazoId, cartaId, cantidad);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si el mazo o la carta no existen, el service lanzará un error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/mazo/{mazoId}/carta/{cartaId}")
    public ResponseEntity<String> quitarCartaDeMazo(
            @PathVariable Long mazoId, 
            @PathVariable Long cartaId) {
        try {
            String resultado = cartaMazoService.eliminarCartaDeMazo(mazoId, cartaId);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

