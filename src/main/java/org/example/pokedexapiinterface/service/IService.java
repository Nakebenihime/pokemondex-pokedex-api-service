package org.example.pokedexapiinterface.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Optional;

public interface IService<T, U> {
    PagedModel<U> findAll(Pageable pageable);

    PagedModel<U> findAllByPokemonTypes(Pageable pageable, List<String> types);

    Optional<T> findByName(String name);
}