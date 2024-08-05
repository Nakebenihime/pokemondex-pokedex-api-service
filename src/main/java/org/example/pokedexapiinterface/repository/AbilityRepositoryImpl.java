package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Ability;
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
public class AbilityRepositoryImpl implements AbilityRepositoryCustom {

    private final MongoSearchUtil<Ability> searchUtil;

    @Autowired
    public AbilityRepositoryImpl(MongoTemplate mongoTemplate) {
        this.searchUtil = new MongoSearchUtil<>(mongoTemplate);
    }

    @Override
    public Page<Ability> search(String name, String description, Integer generation, Pageable pageable) {
        List<Criteria> criteria = new ArrayList<>();

        if (name != null) {
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if (description != null) {
            criteria.add(Criteria.where("description").regex(description, "i"));
        }
        if (generation != null) {
            criteria.add(Criteria.where("generation").is(generation));
        }

        return searchUtil.search(Ability.class, criteria, pageable);
    }
}
