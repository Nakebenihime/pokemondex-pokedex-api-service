package org.example.pokedexapiinterface.service;

import org.example.pokedexapiinterface.viewmodel.PokemonDTO;
import org.example.pokedexapiinterface.viewmodel.PokemonMinimalDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;

;

public interface IPokemonService {
    PagedModel<PokemonMinimalDTO> findAll(Pageable pageable);

    Optional<PokemonDTO> findByName(String name);

    PagedModel<PokemonMinimalDTO> search(String name, List<String> types, Pageable pageable);
}