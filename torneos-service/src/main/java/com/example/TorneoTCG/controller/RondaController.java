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
import com.example.TorneoTCG.dto.RondaDTO;
import com.example.TorneoTCG.model.Ronda;
import com.example.TorneoTCG.service.PartidaService;
import com.example.TorneoTCG.service.RondaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rondas")
public class RondaController {

    @Autowired
    private RondaService rondaService;

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<RondaDTO>> todas() {
        List<RondaDTO> lista = rondaService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{idRonda}/partidas")
    public ResponseEntity<?> partidasPorRonda(@PathVariable Long idRonda) {
        try {
            List<PartidaDTO> lista = partidaService.obtenerPorRonda(idRonda);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        try {
            RondaDTO dto = rondaService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Ronda ronda) {
        try {
            RondaDTO dto = rondaService.guardar(ronda);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en el registro de datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Ronda ronda) {
    try {
        RondaDTO dto = rondaService.actualizar(id, ronda);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    } catch (RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>("Error al actualizar datos", HttpStatus.BAD_REQUEST);
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
    String resultado = rondaService.eliminar(id);
    if (resultado.contains("elimin")) {
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}
}
