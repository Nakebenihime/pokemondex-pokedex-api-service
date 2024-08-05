package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Move;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MoveRepositoryCustom {
    Page<Move> search(String name, String type, String category, Integer power, Integer accuracy, Integer pp, String description, Pageable pageable);
}
