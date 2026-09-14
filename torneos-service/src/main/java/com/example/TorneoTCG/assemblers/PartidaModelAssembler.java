package com.example.TorneoTCG.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.TorneoTCG.controller.v2.PartidaControllerV2;
import com.example.TorneoTCG.dto.PartidaDTO;

@Component
public class PartidaModelAssembler implements RepresentationModelAssembler<PartidaDTO, EntityModel<PartidaDTO>> {

    @Override
    public EntityModel<PartidaDTO> toModel(PartidaDTO partida) {
        return EntityModel.of(partida,
                linkTo(methodOn(PartidaControllerV2.class).porId(partida.getId())).withSelfRel(),
                linkTo(methodOn(PartidaControllerV2.class).todas()).withRel("partidas"));
    }
}
