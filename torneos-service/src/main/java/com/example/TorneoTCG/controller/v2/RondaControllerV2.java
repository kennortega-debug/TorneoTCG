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

import com.example.TorneoTCG.assemblers.RondaModelAssembler;
import com.example.TorneoTCG.dto.RondaDTO;
import com.example.TorneoTCG.model.Ronda;
import com.example.TorneoTCG.service.RondaService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("rondaControllerV2")
@RequestMapping(value = "/api/v2/rondas", produces = MediaTypes.HAL_JSON_VALUE)
public class RondaControllerV2 {

    @Autowired
    private RondaService rondaService;

    @Autowired
    private RondaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RondaDTO>>> todas() {
        List<EntityModel<RondaDTO>> rondas = rondaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (rondas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<RondaDTO>> collectionModel = CollectionModel.of(rondas,
                linkTo(methodOn(RondaControllerV2.class).todas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RondaDTO>> porId(@PathVariable Long id) {
        try {
            RondaDTO dto = rondaService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<RondaDTO>> registrar(@Valid @RequestBody Ronda ronda) {
        RondaDTO newRonda = rondaService.guardar(ronda);

        return ResponseEntity
                .created(linkTo(methodOn(RondaControllerV2.class).porId(newRonda.getId())).toUri())
                .body(assembler.toModel(newRonda));
    }
}
