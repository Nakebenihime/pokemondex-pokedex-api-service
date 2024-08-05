package org.example.pokedexapiinterface.repository;

import org.example.pokedexapiinterface.model.Move;
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
public class MoveRepositoryImpl implements MoveRepositoryCustom {

    private final MongoSearchUtil<Move> searchUtil;

    @Autowired
    public MoveRepositoryImpl(MongoTemplate mongoTemplate) {
        this.searchUtil = new MongoSearchUtil<>(mongoTemplate);
    }

    @Override
    public Page<Move> search(String name, String type, String category, Integer power, Integer accuracy, Integer pp, String description, Pageable pageable) {
        List<Criteria> criteria = new ArrayList<>();

        if (name != null) {
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if (type != null) {
            criteria.add(Criteria.where("type").regex(type, "i"));
        }
        if (category != null) {
            criteria.add(Criteria.where("category").regex(category, "i"));
        }
        if (power != null) {
            criteria.add(Criteria.where("power").is(power));
        }
        if (accuracy != null) {
            criteria.add(Criteria.where("accuracy").is(accuracy));
        }
        if (pp != null) {
            criteria.add(Criteria.where("pp").is(pp));
        }
        if (description != null) {
            criteria.add(Criteria.where("description").regex(description, "i"));
        }

        return searchUtil.search(Move.class, criteria, pageable);
    }
}
