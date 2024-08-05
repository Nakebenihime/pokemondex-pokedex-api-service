package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Move;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoveRepository extends MongoRepository<Move, String>, MoveRepositoryCustom {
    Optional<Move> findByName(String name);
}
