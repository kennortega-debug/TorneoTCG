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

import com.example.TorneoTCG.dto.OrganizadorDTO;
import com.example.TorneoTCG.model.Organizador;
import com.example.TorneoTCG.service.OrganizadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/organizadores")

public class OrganizadorController {

    @Autowired

    private  OrganizadorService organizadorService;

    public OrganizadorController(OrganizadorService organizadorService) {
        this.organizadorService = organizadorService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizadorDTO>> obtenerTodos() {
        List<OrganizadorDTO> organizadores = organizadorService.obtenerTodos();
        return new ResponseEntity<>(organizadores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try{
            OrganizadorDTO organizadorDTO = organizadorService.obtenerPorId(id);
            return  new ResponseEntity<>(organizadorDTO, HttpStatus.OK);

        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Organizador organizador){
        try{
            OrganizadorDTO DTO = organizadorService.Guardar(organizador);
            return new ResponseEntity<>(DTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error al registrar el organizador", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizadorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody OrganizadorDTO organizadorDTO) {
        return ResponseEntity.ok(organizadorService.actualizar(id, organizadorDTO));
    }
}
