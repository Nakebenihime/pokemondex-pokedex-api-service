package org.example.pokedexapiinterface.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface IService<T, U> {
    PagedModel<U> findAll(Pageable pageable);

    Optional<T> findByName(String name);
}