package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String>, PokemonRepositoryCustom {
    Optional<Pokemon> findByName(String name);
}
