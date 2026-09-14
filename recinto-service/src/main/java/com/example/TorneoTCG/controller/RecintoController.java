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

import com.example.TorneoTCG.dto.RecintoDTO;
import com.example.TorneoTCG.model.Recinto;
import com.example.TorneoTCG.service.RecintoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/recintos")

public class RecintoController {

    @Autowired
    private RecintoService recintoService;

    public RecintoController(RecintoService recintoService) {
        this.recintoService = recintoService;
    }

    @GetMapping
    public ResponseEntity<List<RecintoDTO>> obtenerTodos() {
        List<RecintoDTO> recintos = recintoService.obtenerTodos();
        return new ResponseEntity<>(recintos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try{
            RecintoDTO recintoDTO = recintoService.obtenerPorId(id);
            return  new ResponseEntity<>(recintoDTO, HttpStatus.OK);

        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Recinto recinto){
        try{
            RecintoDTO DTO = recintoService.Guardar(recinto);
            return new ResponseEntity<>(DTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error al registrar el recinto", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecintoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RecintoDTO recintoDTO) {
        return ResponseEntity.ok(recintoService.actualizar(id, recintoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRecinto(@PathVariable Long id) {
        try{
            String mensaje = recintoService.eliminar(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al eliminar el recinto", HttpStatus.NOT_FOUND);
        }
    }
}
