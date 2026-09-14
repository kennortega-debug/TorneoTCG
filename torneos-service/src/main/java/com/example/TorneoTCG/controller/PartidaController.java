package com.example.TorneoTCG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TorneoTCG.dto.PartidaDTO;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.service.PartidaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<PartidaDTO>> todas() {
        List<PartidaDTO> lista = partidaService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        try {
            PartidaDTO dto = partidaService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Partida partida) {
        try {
            PartidaDTO dto = partidaService.guardar(partida);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en el registro de datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Partida partida) {
    try {
        PartidaDTO dto = partidaService.actualizar(id, partida);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>("Error al actualizar datos", HttpStatus.BAD_REQUEST);
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
    String resultado = partidaService.eliminar(id);
    if (resultado.contains("elimin")) {
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}

}
