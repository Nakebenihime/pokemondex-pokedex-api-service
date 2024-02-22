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
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.pokedexapiinterface.utils.StringUtils.convertToTitleCase;

@Slf4j
@Service
public class IPokemonServiceImpl implements IService<PokemonDTO, PokemonMinimalDTO> {

    private final @NonNull PokemonRepository pokemonRepository;
    private final @NonNull PokemonDTOAssembler pokemonDTOAssembler;
    private final @NonNull PokemonMinimalDTOAssembler pokemonMinimalDTOAssembler;

    public IPokemonServiceImpl(@NonNull PokemonRepository pokemonRepository, @NonNull PokemonDTOAssembler pokemonDTOAssembler, @NonNull PokemonMinimalDTOAssembler pokemonMinimalDTOAssembler) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonDTOAssembler = pokemonDTOAssembler;
        this.pokemonMinimalDTOAssembler = pokemonMinimalDTOAssembler;
    }

    @Override
    public PagedModel<PokemonMinimalDTO> findAll(Pageable pageable) {
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException("This usually occurs when the pagination parameters are incorrect; please check the number of pages, the size and the sorting criteria. Example request: GET /api/pokemons?page=2&size=20&sort=name,asc");
        }
        return pokemonMinimalDTOAssembler.toPagedModel(pokemons);
    }

    @Override
    public Optional<PokemonDTO> findByName(String name) {
        return Optional.ofNullable(pokemonRepository.findByName(convertToTitleCase(name)).map(pokemonDTOAssembler::toModel)
                .orElseThrow(() -> new PokemonNotFoundException(String.format("This usually occurs when the specified Pokemon name (%s) can't be found, make sure the name is spelled correctly and includes any necessary hyphens (e.g., 'Charizard (Mega Charizard Y)'). Example request: GET /api/v1/pokemons/charizard-mega-charizard-y", name))));
    }
}
