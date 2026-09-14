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

import com.example.TorneoTCG.assemblers.ResultadoModelAssembler;
import com.example.TorneoTCG.dto.ResultadoDTO;
import com.example.TorneoTCG.model.Resultado;
import com.example.TorneoTCG.service.ResultadoService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("resultadoControllerV2")
@RequestMapping(value = "/api/v2/resultados", produces = MediaTypes.HAL_JSON_VALUE)
public class ResultadoControllerV2 {

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private ResultadoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResultadoDTO>>> todas() {
        List<EntityModel<ResultadoDTO>> resultados = resultadoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<ResultadoDTO>> collectionModel = CollectionModel.of(resultados,
                linkTo(methodOn(ResultadoControllerV2.class).todas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ResultadoDTO>> porId(@PathVariable Long id) {
        try {
            ResultadoDTO dto = resultadoService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<ResultadoDTO>> registrar(@Valid @RequestBody Resultado resultado) {
        ResultadoDTO newResultado = resultadoService.guardar(resultado);

        return ResponseEntity
                .created(linkTo(methodOn(ResultadoControllerV2.class).porId(newResultado.getId())).toUri())
                .body(assembler.toModel(newResultado));
    }
}
