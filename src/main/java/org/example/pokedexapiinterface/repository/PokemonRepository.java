package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    @Query("{'types': {$all: ?0}}")
    Page<Pokemon> findAllByPokemonTypes(Pageable pageable, List<String> types);

    Optional<Pokemon> findByName(String name);
}
