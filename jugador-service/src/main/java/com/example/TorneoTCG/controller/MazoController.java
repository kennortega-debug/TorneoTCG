package com.example.TorneoTCG.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.TorneoTCG.dto.MazoDTO;
import com.example.TorneoTCG.model.Mazo;
import com.example.TorneoTCG.service.MazoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mazos")
public class MazoController {

    @Autowired
    private MazoService mazoService;

    @GetMapping
    public ResponseEntity<List<MazoDTO>> todosLosMazos() {
        List<MazoDTO> mazos = mazoService.obtenerTodos();
        if (mazos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mazos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MazoDTO> buscarPorId(@PathVariable Long id) {
        try {
            MazoDTO mazo = mazoService.buscarPorId(id);
            return new ResponseEntity<>(mazo, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
public ResponseEntity<MazoDTO> crearMazo(@Valid @RequestBody Mazo mazo) {

    try {

        Mazo guardado = mazoService.guardar(mazo);

        MazoDTO dto = mazoService.buscarPorId(guardado.getId());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    } catch (Exception e) {

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMazo(@PathVariable Long id) {
        String resultado = mazoService.eliminar(id);

        if (resultado.contains("exitosamente") || resultado.contains("eliminado")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
