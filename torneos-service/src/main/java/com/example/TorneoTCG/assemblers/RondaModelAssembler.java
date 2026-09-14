package com.example.TorneoTCG.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.TorneoTCG.controller.v2.RondaControllerV2;
import com.example.TorneoTCG.dto.RondaDTO;

@Component
public class RondaModelAssembler implements RepresentationModelAssembler<RondaDTO, EntityModel<RondaDTO>> {

    @Override
    public EntityModel<RondaDTO> toModel(RondaDTO ronda) {
        return EntityModel.of(ronda,
                linkTo(methodOn(RondaControllerV2.class).porId(ronda.getId())).withSelfRel(),
                linkTo(methodOn(RondaControllerV2.class).todas()).withRel("rondas"));
    }
}
