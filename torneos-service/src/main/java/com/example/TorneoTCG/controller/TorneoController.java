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

import com.example.TorneoTCG.dto.RankingDTO;
import com.example.TorneoTCG.dto.RondaDTO;
import com.example.TorneoTCG.dto.TorneoDTO;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.service.RankingService;
import com.example.TorneoTCG.service.RondaService;
import com.example.TorneoTCG.service.TorneoLogicaService;
import com.example.TorneoTCG.service.TorneoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/torneos")
public class TorneoController {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private TorneoLogicaService torneoLogicaService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private RondaService rondaService;

    @GetMapping
    public ResponseEntity<List<TorneoDTO>> todas() {
        List<TorneoDTO> lista = torneoService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        try {
            TorneoDTO dto = torneoService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Torneo torneo) {
        try {
            TorneoDTO dto = torneoService.guardar(torneo);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en el registro de datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Torneo torneo) {
        try {
            TorneoDTO dto = torneoService.actualizar(id, torneo);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar datos", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        String resultado = torneoService.eliminar(id);
        if (resultado.contains("elimin")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idTorneo}/rondas")
    public ResponseEntity<?> obtenerRondasPorTorneo(@PathVariable Long idTorneo) {
        try {
            List<RondaDTO> rondas = rondaService.obtenerPorTorneo(idTorneo);
            return new ResponseEntity<>(rondas, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<?> iniciarTorneo(@PathVariable Long id) {
        try {
            String mensaje = torneoLogicaService.iniciarTorneo(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/generar-rondas")
    public ResponseEntity<?> generarRondas(@PathVariable Long id) {
        try {
            String mensaje = torneoLogicaService.generarRondas(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/rondas/{idRonda}/generar-partidas")
    public ResponseEntity<?> generarPartidas(@PathVariable Long idRonda) {
        try {
            String mensaje = torneoLogicaService.generarPartidas(idRonda);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarTorneo(@PathVariable Long id) {
        try {
            String mensaje = torneoLogicaService.finalizarTorneo(id);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idTorneo}/ranking")
    public ResponseEntity<?> obtenerRanking(@PathVariable Long idTorneo) {
        try {
            List<RankingDTO> ranking = rankingService.obtenerRankingPorTorneo(idTorneo);
            return new ResponseEntity<>(ranking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
