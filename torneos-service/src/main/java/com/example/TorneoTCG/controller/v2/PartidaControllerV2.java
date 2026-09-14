package com.example.TorneoTCG.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TorneoTCG.assemblers.PartidaModelAssembler;
import com.example.TorneoTCG.dto.PartidaDTO;
import com.example.TorneoTCG.model.Partida;
import com.example.TorneoTCG.service.PartidaService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("partidaControllerV2")
@RequestMapping(value = "/api/v2/partidas", produces = MediaTypes.HAL_JSON_VALUE)
public class PartidaControllerV2 {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PartidaDTO>>> todas() {
        List<EntityModel<PartidaDTO>> partidas = partidaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (partidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<PartidaDTO>> collectionModel = CollectionModel.of(partidas,
                linkTo(methodOn(PartidaControllerV2.class).todas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PartidaDTO>> porId(@PathVariable Long id) {
        try {
            PartidaDTO dto = partidaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<PartidaDTO>> registrar(@Valid @RequestBody Partida partida) {
        PartidaDTO newPartida = partidaService.guardar(partida);

        return ResponseEntity
                .created(linkTo(methodOn(PartidaControllerV2.class).porId(newPartida.getId())).toUri())
                .body(assembler.toModel(newPartida));
    }
}
