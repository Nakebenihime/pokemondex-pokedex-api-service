package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.AbilityController;
import org.example.pokedexapiinterface.controller.MoveController;
import org.example.pokedexapiinterface.model.Move;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToHyphenatedForm;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MoveDTOAssembler extends RepresentationModelAssemblerSupport<Move, MoveDTO> {

    private final ModelMapper mapper;

    public MoveDTOAssembler(ModelMapper mapper) {
        super(AbilityController.class, MoveDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull MoveDTO toModel(@NonNull Move entity) {
        MoveDTO moveDTO = mapper.map(entity, MoveDTO.class);
        moveDTO.add(linkTo(methodOn(MoveController.class).getMoves(Pageable.unpaged())).withSelfRel());
        return moveDTO;
    }

    public @NonNull MoveDTO toList(@NonNull Move entity) {
        MoveDTO moveDTO = mapper.map(entity, MoveDTO.class);
        moveDTO.add(linkTo(MoveController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return moveDTO;
    }
}
