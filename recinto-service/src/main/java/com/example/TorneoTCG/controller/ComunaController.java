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

import com.example.TorneoTCG.dto.ComunaDTO;
import com.example.TorneoTCG.model.Comuna;
import com.example.TorneoTCG.service.ComunaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comunas")

public class ComunaController {
    
    @Autowired

    private  ComunaService comunaService;

    public ComunaController(ComunaService comunaService) {
        this.comunaService = comunaService;
    }

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> obtenerTodos() {
        List<ComunaDTO> comunas = comunaService.obtenerTodos();
        return new ResponseEntity<>(comunas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try{
            ComunaDTO comunaDTO = comunaService.obtenerPorId(id);
            return  new ResponseEntity<>(comunaDTO, HttpStatus.OK);

        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Comuna comuna){
        try{
            ComunaDTO DTO = comunaService.Guardar(comuna);
            return new ResponseEntity<>(DTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error al registrar la comuna", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ComunaDTO comunaDTO) {
        return ResponseEntity.ok(comunaService.actualizar(id, comunaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarComuna(@PathVariable Long id) {
        try{
            String mensaje = comunaService.eliminar(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al eliminar la comuna", HttpStatus.NOT_FOUND);
        }
    }
}
