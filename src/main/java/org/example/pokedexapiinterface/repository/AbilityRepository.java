package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Ability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbilityRepository extends MongoRepository<Ability, String>, AbilityRepositoryCustom {
    Optional<Ability> findByName(String name);
}
