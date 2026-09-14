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

import com.example.TorneoTCG.assemblers.ParticipacionModelAssembler;
import com.example.TorneoTCG.dto.ParticipacionDTO;
import com.example.TorneoTCG.model.Participacion;
import com.example.TorneoTCG.service.ParticipacionService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("participacionControllerV2")
@RequestMapping(value = "/api/v2/participaciones", produces = MediaTypes.HAL_JSON_VALUE)
public class ParticipacionControllerV2 {

    @Autowired
    private ParticipacionService participacionService;

    @Autowired
    private ParticipacionModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ParticipacionDTO>>> todas() {
        List<EntityModel<ParticipacionDTO>> participaciones = participacionService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (participaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<ParticipacionDTO>> collectionModel = CollectionModel.of(participaciones,
                linkTo(methodOn(ParticipacionControllerV2.class).todas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ParticipacionDTO>> porId(@PathVariable Long id) {
        try {
            ParticipacionDTO dto = participacionService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<ParticipacionDTO>> registrar(@Valid @RequestBody Participacion participacion) {
        ParticipacionDTO newParticipacion = participacionService.guardar(participacion);

        return ResponseEntity
                .created(linkTo(methodOn(ParticipacionControllerV2.class).porId(newParticipacion.getId())).toUri())
                .body(assembler.toModel(newParticipacion));
    }
}
