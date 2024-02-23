package org.example.pokedexapiinterface.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.pokedexapiinterface.exception.PokemonNotFoundException;
import org.example.pokedexapiinterface.model.Pokemon;
import org.example.pokedexapiinterface.repository.PokemonRepository;
import org.example.pokedexapiinterface.viewmodel.PokemonDTO;
import org.example.pokedexapiinterface.viewmodel.PokemonDTOAssembler;
import org.example.pokedexapiinterface.viewmodel.PokemonListDTO;
import org.example.pokedexapiinterface.viewmodel.PokemonListDTOAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.pokedexapiinterface.utils.StringUtils.convertToTitleCaseWBrackets;

@Slf4j
@Service
public class IPokemonServiceImpl implements IService<PokemonDTO, PokemonListDTO> {

    private final @NonNull PokemonRepository pokemonRepository;
    private final @NonNull PokemonDTOAssembler pokemonDTOAssembler;
    private final @NonNull PokemonListDTOAssembler pokemonListDTOAssembler;

    public IPokemonServiceImpl(@NonNull PokemonRepository pokemonRepository, @NonNull PokemonDTOAssembler pokemonDTOAssembler, @NonNull PokemonListDTOAssembler pokemonListDTOAssembler) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonDTOAssembler = pokemonDTOAssembler;
        this.pokemonListDTOAssembler = pokemonListDTOAssembler;
    }

    @Override
    public PagedModel<PokemonListDTO> findAll(Pageable pageable) {
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        if (pokemons.isEmpty()) {
            throw new PokemonNotFoundException("This usually occurs when the pagination parameters are incorrect; please check the number of pages, the size and the sorting criteria. Example request: GET /api/pokemons?page=2&size=20&sort=name,asc");
        }
        return pokemonListDTOAssembler.toPagedModel(pokemons);
    }

    @Override
    public Optional<PokemonDTO> findByName(String name) {
        return Optional.ofNullable(pokemonRepository.findByName(convertToTitleCaseWBrackets(name)).map(pokemonDTOAssembler::toModel)
                .orElseThrow(() -> new PokemonNotFoundException(String.format("This usually occurs when the specified Pokemon name (%s) can't be found, make sure the name is spelled correctly and includes any necessary hyphens (e.g., 'Charizard (Mega Charizard Y)'). Example request: GET /api/v1/pokemons/charizard-mega-charizard-y", name))));
    }
}
