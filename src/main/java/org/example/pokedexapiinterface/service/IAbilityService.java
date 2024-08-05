package org.example.pokedexapiinterface.service;

import org.example.pokedexapiinterface.viewmodel.AbilityDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface IAbilityService {
    PagedModel<AbilityDTO> findAll(Pageable pageable);

    Optional<AbilityDTO> findByName(String name);

    PagedModel<AbilityDTO> search(String name, String description, Integer generation, Pageable pageable);
}
