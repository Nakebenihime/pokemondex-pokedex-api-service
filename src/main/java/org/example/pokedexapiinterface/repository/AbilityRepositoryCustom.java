package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Ability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbilityRepositoryCustom {
    Page<Ability> search(String name, String description, Integer generation, Pageable pageable);
}
