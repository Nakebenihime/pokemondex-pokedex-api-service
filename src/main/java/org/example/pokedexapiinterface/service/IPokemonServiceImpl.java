package org.example.pokedexapiinterface.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.exception.PokemonNotFoundException;
import org.example.pokedexapiinterface.model.Pokemon;
import org.example.pokedexapiinterface.repository.PokemonRepository;
import org.example.pokedexapiinterface.viewmodel.PokemonDTO;
import org.example.pokedexapiinterface.viewmodel.PokemonDTOAssembler;
import org.example.pokedexapiinterface.viewmodel.PokemonMinimalDTO;
import org.example.pokedexapiinterface.viewmodel.PokemonMinimalDTOAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.pokedexapiinterface.utils.StringUtil.convertToTitleCase;

@Slf4j
@Service
public class IPokemonServiceImpl implements IPokemonService {

    private final @NonNull PokemonRepository pokemonRepository;
    private final @NonNull PokemonMinimalDTOAssembler pokemonMinimalDTOAssembler;
    private final @NonNull PokemonDTOAssembler pokemonDTOAssembler;
    private final @NonNull PagedResourcesAssembler<Pokemon> pagedResourcesAssembler;

    public IPokemonServiceImpl(@NonNull PokemonRepository pokemonRepository, @NonNull PokemonMinimalDTOAssembler pokemonMinimalDTOAssembler, @NonNull PokemonDTOAssembler pokemonDTOAssembler, @NonNull PagedResourcesAssembler<Pokemon> pagedResourcesAssembler) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonMinimalDTOAssembler = pokemonMinimalDTOAssembler;
        this.pokemonDTOAssembler = pokemonDTOAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<PokemonMinimalDTO> findAll(Pageable pageable) {
        log.info("Fetching all pokemons with pagination - Page Number: {}, Page Size: {}, Sort: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException(
                    "The query yielded no results. " +
                            "This usually occurs when the pagination settings are not correctly configured, including the page number, page size, or sorting order. " +
                            "For example, you might try: GET /api/pokemons?page=1&size=10&sort=name,asc or GET /api/pokemons?page=1&size=20&sort=generation,desc");
        }
        log.info("Successfully retrieved {} pokemons matching the query.", pokemons.getTotalElements());
        return pagedResourcesAssembler.toModel(pokemons, pokemonMinimalDTOAssembler);
    }

    @Override
    public Optional<PokemonDTO> findByName(String name) {
        return Optional.ofNullable(pokemonRepository.findByName(convertToTitleCase(name, true)).map(pokemonDTOAssembler::toModel)
                .orElseThrow(() -> new PokemonNotFoundException(String.format(
                        "The specified Pokemon name ('%s') could not be found. " +
                                "To avoid this issue, ensure that the name is spelled correctly and that any spaces in the name are replaced with hyphens. " +
                                "For example, if you're trying to access the Pokemon 'Charizard (Mega Charizard X)', you should format it as 'charizard-mega-charizard-y' in your request, like so: GET /api/v1/pokemons/charizard-mega-charizard-y", name))
                )
        );
    }

    @Override
    public PagedModel<PokemonMinimalDTO> search(String name, List<String> types, Pageable pageable) {
        if (types.size() > 2) {
            throw new IllegalArgumentException("This usually occurs when the number of types provided is invalid. " +
                    "To avoid this issue, ensure that the number of types provided must be between 1 and 2. " +
                    "For example, you might try: GET /api/v1/pokemons/search?types=fire or GET /api/v1/pokemons/search?types=fire,dragon or GET /api/v1/pokemons/search?types=fire&types=dragon");
        }
        Page<Pokemon> pokemons = pokemonRepository.search(name, types, pageable);
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException(
                    "The query yielded no results. " +
                            "This usually occurs when the search or pagination settings are not correctly configured. " +
                            "For example, you might try: GET /api/v1/pokemons/search?types=fire&name=Char");
        }
        log.info("Successfully retrieved {} pokemons matching the query.", pokemons.getTotalElements());
        return pagedResourcesAssembler.toModel(pokemons, pokemonMinimalDTOAssembler);
    }
}