package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.PokemonController;
import org.example.pokedexapiinterface.model.Pokemon;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToHyphenatedForm;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PokemonMinimalDTOAssembler extends RepresentationModelAssemblerSupport<Pokemon, PokemonMinimalDTO> {

    private final ModelMapper mapper;

    public PokemonMinimalDTOAssembler(ModelMapper mapper) {
        super(PokemonController.class, PokemonMinimalDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull PokemonMinimalDTO toModel(@NonNull Pokemon entity) {
        PokemonMinimalDTO pokemonMinimalDTO = mapper.map(entity, PokemonMinimalDTO.class);
        pokemonMinimalDTO.add(linkTo(PokemonController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return pokemonMinimalDTO;
    }
}