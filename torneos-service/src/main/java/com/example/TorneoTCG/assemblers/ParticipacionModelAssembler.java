package com.example.TorneoTCG.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.TorneoTCG.controller.v2.ParticipacionControllerV2;
import com.example.TorneoTCG.dto.ParticipacionDTO;

@Component
public class ParticipacionModelAssembler implements RepresentationModelAssembler<ParticipacionDTO, EntityModel<ParticipacionDTO>> {

    @Override
    public EntityModel<ParticipacionDTO> toModel(ParticipacionDTO participacion) {
        return EntityModel.of(participacion,
                linkTo(methodOn(ParticipacionControllerV2.class).porId(participacion.getId())).withSelfRel(),
                linkTo(methodOn(ParticipacionControllerV2.class).todas()).withRel("participaciones"));
    }
}
