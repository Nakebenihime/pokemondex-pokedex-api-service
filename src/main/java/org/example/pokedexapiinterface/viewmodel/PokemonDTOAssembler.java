package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.PokemonController;
import org.example.pokedexapiinterface.model.Pokemon;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PokemonDTOAssembler extends RepresentationModelAssemblerSupport<Pokemon, PokemonDTO> {

    private final ModelMapper mapper;

    public PokemonDTOAssembler(ModelMapper mapper) {
        super(PokemonController.class, PokemonDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull PokemonDTO toModel(@NonNull Pokemon entity) {
        PokemonDTO pokemonDTO = mapper.map(entity, PokemonDTO.class);
        pokemonDTO.add(linkTo(methodOn(PokemonController.class).getPokemons(Pageable.unpaged())).withSelfRel());
        return pokemonDTO;
    }
}
