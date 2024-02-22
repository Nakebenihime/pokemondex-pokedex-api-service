package org.example.pokedexapiinterface.viewmodel;

import org.example.pokedexapiinterface.controller.PokemonController;
import org.example.pokedexapiinterface.model.Pokemon;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.StreamSupport;

import static org.example.pokedexapiinterface.utils.StringUtils.convertToHyphenatedForm;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PokemonMinimalDTOAssembler extends RepresentationModelAssemblerSupport<Pokemon, PokemonMinimalDTO> {

    private final ModelMapper mapper;

    public PokemonMinimalDTOAssembler(ModelMapper mapper) {
        super(PokemonController.class, PokemonMinimalDTO.class);
        this.mapper = mapper;
    }

    @Override
    public PokemonMinimalDTO toModel(Pokemon entity) {
        PokemonMinimalDTO pokemonMinimalDTO = mapper.map(entity, PokemonMinimalDTO.class);
        pokemonMinimalDTO.add(linkTo(PokemonController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return pokemonMinimalDTO;
    }

    @Override
    public CollectionModel<PokemonMinimalDTO> toCollectionModel(Iterable<? extends Pokemon> entities) {
        return CollectionModel.of(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList());
    }

    public PagedModel<PokemonMinimalDTO> toPagedModel(Page<Pokemon> page) {
        Collection<PokemonMinimalDTO> pokemonMinimalDTOS = toCollectionModel(page.getContent()).getContent();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<PokemonMinimalDTO> pagedModel = PagedModel.of(pokemonMinimalDTOS, pageMetadata);
        pagedModel.add(linkTo(methodOn(PokemonController.class).getPokemons(page.getPageable())).withSelfRel());
        return pagedModel;
    }
}
