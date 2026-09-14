package com.example.TorneoTCG.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.TorneoTCG.controller.v2.TorneoControllerV2;
import com.example.TorneoTCG.dto.TorneoDTO;

@Component
public class TorneoModelAssembler implements RepresentationModelAssembler<TorneoDTO, EntityModel<TorneoDTO>> {

    @Override
    public EntityModel<TorneoDTO> toModel(TorneoDTO torneo) {
        return EntityModel.of(torneo,
                linkTo(methodOn(TorneoControllerV2.class).porId(torneo.getId())).withSelfRel(),
                linkTo(methodOn(TorneoControllerV2.class).todas()).withRel("torneos"));
    }
}
