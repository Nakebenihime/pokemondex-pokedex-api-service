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
public class IPokemonServiceImpl implements IService<PokemonDTO, PokemonMinimalDTO> {
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
    public PagedModel<PokemonMinimalDTO> findAllByPokemonTypes(Pageable pageable, List<String> types) {
        if (types.size() > 2) {
            throw new IllegalArgumentException("This usually occurs when the number of types provided is invalid. please provide minimum one and maximum two types.");
        }
        Page<Pokemon> pokemons = pokemonRepository.findAllByPokemonTypes(pageable, types);

        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException("This usually occurs when the pagination parameters are incorrect; please check the number of pages, the size and the sorting criteria. Example request: GET /api/pokemons?page=2&size=20&sort=name,asc");
        }
        return pagedResourcesAssembler.toModel(pokemons, pokemonMinimalDTOAssembler);
    }

    @Override
    public PagedModel<PokemonMinimalDTO> findAll(Pageable pageable) {
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException("This usually occurs when the pagination parameters are incorrect; please check the number of pages, the size and the sorting criteria. Example request: GET /api/pokemons?page=2&size=20&sort=name,asc");
        }
        return pagedResourcesAssembler.toModel(pokemons, pokemonMinimalDTOAssembler);
    }

    @Override
    public Optional<PokemonDTO> findByName(String name) {
        return Optional.ofNullable(pokemonRepository.findByName(convertToTitleCase(name, true)).map(pokemonDTOAssembler::toModel)
                .orElseThrow(() -> new PokemonNotFoundException(String.format("This usually occurs when the specified Pokemon name (%s) can't be found, make sure the name is spelled correctly and includes any necessary hyphens (e.g., 'Charizard (Mega Charizard Y)'). Example request: GET /api/v1/pokemons/charizard-mega-charizard-y", name))));
    }
}