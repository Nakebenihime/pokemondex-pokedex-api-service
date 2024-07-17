package org.example.pokedexapiinterface.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface ISingleService<T> {
    PagedModel<T> findAll(Pageable pageable);

    Optional<T> findByName(String name);
}
