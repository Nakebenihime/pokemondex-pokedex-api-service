package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PokemonRepositoryCustom {
    Page<Pokemon> search(String name, List<String> types, Pageable pageable);
}
