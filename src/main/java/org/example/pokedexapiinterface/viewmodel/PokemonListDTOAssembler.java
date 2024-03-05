package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.PokemonController;
import org.example.pokedexapiinterface.model.Pokemon;
import org.example.pokedexapiinterface.utils.PaginationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.stream.StreamSupport;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToHyphenatedForm;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PokemonListDTOAssembler extends RepresentationModelAssemblerSupport<Pokemon, PokemonListDTO> {

    private final ModelMapper mapper;

    public PokemonListDTOAssembler(ModelMapper mapper) {
        super(PokemonController.class, PokemonListDTO.class);
        this.mapper = mapper;
    }

    @Override
    public @NonNull PokemonListDTO toModel(@NonNull Pokemon entity) {
        PokemonListDTO pokemonListDTO = mapper.map(entity, PokemonListDTO.class);
        pokemonListDTO.add(linkTo(PokemonController.class).slash((convertToHyphenatedForm(entity.getName()))).withSelfRel());
        return pokemonListDTO;
    }

    @Override
    public @NonNull CollectionModel<PokemonListDTO> toCollectionModel(Iterable<? extends Pokemon> entities) {
        return CollectionModel.of(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(this::toModel)
                        .toList());
    }

    public PagedModel<PokemonListDTO> toPagedModel(Page<Pokemon> page) {
        Collection<PokemonListDTO> pokemonListDTOS = toCollectionModel(page.getContent()).getContent();
        PagedModel.PageMetadata pageMetadata = PaginationUtil.getPageMetadata(page);
        PagedModel<PokemonListDTO> pagedModel = PagedModel.of(pokemonListDTOS, pageMetadata);
        pagedModel.add(linkTo(PokemonController.class).withSelfRel());
        UriComponentsBuilder uriBuilder = linkTo(methodOn(PokemonController.class).getPokemons(Pageable.unpaged())).toUriComponentsBuilder();
        PaginationUtil.addPaginationLinks(pagedModel, uriBuilder, page);
        return pagedModel;
    }
}