package com.example.TorneoTCG.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.TorneoTCG.dto.JugadorDTO;
import com.example.TorneoTCG.model.Jugador;
import com.example.TorneoTCG.service.JugadorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> listarTodos() {
        List<JugadorDTO> jugadores = jugadorService.obtenerTodos();
        if (jugadores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jugadores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> buscarPorId(@PathVariable Integer id) {
        try {
            JugadorDTO jugador = jugadorService.buscarPorId(id);
            return new ResponseEntity<>(jugador, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Jugador> agregarJugador(@Valid @RequestBody Jugador jugador) {
        try {
            Jugador guardado = jugadorService.guardar(jugador);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarJugador(@PathVariable Integer id) {
        String resultado = jugadorService.eliminar(id);
        
        if (resultado.contains("descalificado")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
