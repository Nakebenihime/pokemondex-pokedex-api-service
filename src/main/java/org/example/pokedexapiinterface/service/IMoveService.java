package org.example.pokedexapiinterface.service;

import org.example.pokedexapiinterface.viewmodel.MoveDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface IMoveService {
    PagedModel<MoveDTO> findAll(Pageable pageable);

    Optional<MoveDTO> findByName(String name);

    PagedModel<MoveDTO> search(String name, String type, String category, Integer power, Integer accuracy, Integer pp, String description, Pageable pageable);
}
