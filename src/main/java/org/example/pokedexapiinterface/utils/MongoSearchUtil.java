package org.example.pokedexapiinterface.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoSearchUtil<T> {

    private final MongoTemplate mongoTemplate;

    public MongoSearchUtil(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Page<T> search(Class<T> entityClass, List<Criteria> criteria, Pageable pageable) {
        Query query = new Query();

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, entityClass);
        query.with(pageable);

        List<T> results = mongoTemplate.find(query, entityClass);
        return new PageImpl<>(results, pageable, total);
    }
}
