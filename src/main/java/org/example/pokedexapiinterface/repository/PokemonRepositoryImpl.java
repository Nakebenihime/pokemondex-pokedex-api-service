package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Pokemon;
import org.example.pokedexapiinterface.utils.MongoSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PokemonRepositoryImpl implements PokemonRepositoryCustom {

    private final MongoSearchUtil<Pokemon> searchUtil;

    @Autowired
    public PokemonRepositoryImpl(MongoTemplate mongoTemplate) {
        this.searchUtil = new MongoSearchUtil<>(mongoTemplate);
    }

    @Override
    public Page<Pokemon> search(String name, List<String> types, Pageable pageable) {
        List<Criteria> criteria = new ArrayList<>();

        if (name != null) {
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if (types != null && !types.isEmpty()) {
            criteria.add(Criteria.where("types").all(types));
        }
        return searchUtil.search(Pokemon.class, criteria, pageable);
    }
}
