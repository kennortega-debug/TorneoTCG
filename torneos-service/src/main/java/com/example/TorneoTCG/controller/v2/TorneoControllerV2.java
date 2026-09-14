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

import com.example.TorneoTCG.assemblers.TorneoModelAssembler;
import com.example.TorneoTCG.dto.TorneoDTO;
import com.example.TorneoTCG.model.Torneo;
import com.example.TorneoTCG.service.TorneoService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("torneoControllerV2")
@RequestMapping(value = "/api/v2/torneos", produces = MediaTypes.HAL_JSON_VALUE)
public class TorneoControllerV2 {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private TorneoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TorneoDTO>>> todas() {
        List<EntityModel<TorneoDTO>> torneos = torneoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (torneos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<TorneoDTO>> collectionModel = CollectionModel.of(torneos,
                linkTo(methodOn(TorneoControllerV2.class).todas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TorneoDTO>> porId(@PathVariable Long id) {
        try {
            TorneoDTO dto = torneoService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<TorneoDTO>> registrar(@Valid @RequestBody Torneo torneo) {
        TorneoDTO newTorneo = torneoService.guardar(torneo);

        return ResponseEntity
                .created(linkTo(methodOn(TorneoControllerV2.class).porId(newTorneo.getId())).toUri())
                .body(assembler.toModel(newTorneo));
    }
}
