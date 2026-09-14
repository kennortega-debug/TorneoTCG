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

import com.example.TorneoTCG.dto.RegionDTO;
import com.example.TorneoTCG.model.Region;
import com.example.TorneoTCG.service.RegionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/regiones")

public class RegionController {

    @Autowired
    private RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<RegionDTO>> obtenerTodos() {
        List<RegionDTO> regiones = regionService.obtenerTodos();
        return new ResponseEntity<>(regiones, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try{
            RegionDTO regionDTO = regionService.obtenerPorId(id);
            return  new ResponseEntity<>(regionDTO, HttpStatus.OK);

        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Region region){
        try{
            RegionDTO DTO = regionService.Guardar(region);
            return new ResponseEntity<>(DTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Error al registrar la región", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.actualizar(id, regionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRegion(@PathVariable Long id) {
        try{
            String mensaje = regionService.eliminar(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error al eliminar la región", HttpStatus.NOT_FOUND);
        }
    }
}
