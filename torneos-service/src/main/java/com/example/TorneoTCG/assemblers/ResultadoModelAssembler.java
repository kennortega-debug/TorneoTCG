package com.example.TorneoTCG.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.TorneoTCG.controller.v2.ResultadoControllerV2;
import com.example.TorneoTCG.dto.ResultadoDTO;

@Component
public class ResultadoModelAssembler implements RepresentationModelAssembler<ResultadoDTO, EntityModel<ResultadoDTO>> {

    @Override
    public EntityModel<ResultadoDTO> toModel(ResultadoDTO resultado) {
        return EntityModel.of(resultado,
                linkTo(methodOn(ResultadoControllerV2.class).porId(resultado.getId())).withSelfRel(),
                linkTo(methodOn(ResultadoControllerV2.class).todas()).withRel("resultados"));
    }
}
