package org.example.pokedexapiinterface.viewmodel;

import lombok.NonNull;
import org.example.pokedexapiinterface.controller.PokemonController;
import org.example.pokedexapiinterface.model.Pokemon;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.stream.StreamSupport;

import static org.example.pokedexapiinterface.utils.StringUtils.convertToHyphenatedForm;
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
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<PokemonListDTO> pagedModel = PagedModel.of(pokemonListDTOS, pageMetadata);
        pagedModel.add(linkTo(methodOn(PokemonController.class).getPokemons(page.getPageable())).withSelfRel());
        addQueryParameters(pagedModel, page);
        return pagedModel;
    }

    private void addQueryParameters(PagedModel<PokemonListDTO> pagedModel, Page<Pokemon> page) {
        UriComponentsBuilder uriBuilder = buildUriForPagination(page);

        if (!page.isFirst()) {
            PageRequest firstPageable = PageRequest.of(0, page.getSize(), page.getSort());
            uriBuilder.replaceQueryParam("page", firstPageable.getPageNumber());
            uriBuilder.replaceQueryParam("size", firstPageable.getPageSize());
            addSortingQueryParams(uriBuilder, firstPageable.getSort());
            pagedModel.add(Link.of(uriBuilder.toUriString(), "first"));
        }

        if (page.hasNext()) {
            PageRequest nextPageable = PageRequest.of(page.getNumber() + 1, page.getSize(), page.getSort());
            uriBuilder.replaceQueryParam("page", nextPageable.getPageNumber());
            uriBuilder.replaceQueryParam("size", nextPageable.getPageSize());
            addSortingQueryParams(uriBuilder, nextPageable.getSort());
            pagedModel.add(Link.of(uriBuilder.toUriString(), "next"));
        }

        if (page.hasPrevious()) {
            PageRequest prevPageable = PageRequest.of(page.getNumber() - 1, page.getSize(), page.getSort());
            uriBuilder.replaceQueryParam("page", prevPageable.getPageNumber());
            uriBuilder.replaceQueryParam("size", prevPageable.getPageSize());
            addSortingQueryParams(uriBuilder, prevPageable.getSort());
            pagedModel.add(Link.of(uriBuilder.toUriString(), "previous"));
        }

        if (!page.isLast()) {
            PageRequest lastPageable = PageRequest.of(page.getTotalPages() - 1, page.getSize(), page.getSort());
            uriBuilder.replaceQueryParam("page", lastPageable.getPageNumber());
            uriBuilder.replaceQueryParam("size", lastPageable.getPageSize());
            addSortingQueryParams(uriBuilder, lastPageable.getSort());
            pagedModel.add(Link.of(uriBuilder.toUriString(), "last"));
        }
    }

    private UriComponentsBuilder buildUriForPagination(Page<Pokemon> page) {
        return linkTo(methodOn(PokemonController.class).getPokemons(page.getPageable())).toUriComponentsBuilder();
    }

    private void addSortingQueryParams(UriComponentsBuilder uriBuilder, Sort sort) {
        uriBuilder.replaceQueryParam("sort");
        sort.forEach(order -> uriBuilder.queryParam("sort", String.format("%s,%s", order.getProperty(), order.getDirection())));
    }
}
