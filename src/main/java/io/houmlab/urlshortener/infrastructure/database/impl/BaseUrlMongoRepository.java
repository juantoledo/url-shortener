package io.houmlab.urlshortener.infrastructure.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import io.houmlab.urlshortener.infrastructure.database.UrlMongoRepository;
import io.houmlab.urlshortener.infrastructure.database.UrlRepository;
import io.houmlab.urlshortener.model.Url;



@Repository
public class BaseUrlMongoRepository implements UrlRepository {

    private final UrlMongoRepository database;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BaseUrlMongoRepository(UrlMongoRepository database, MongoTemplate mongoTemplate) {
        this.database = database;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Url findByCode(String code) {
        return database.findById(code).get();
    }

    @Override
    public void deleteByCode(String code) {
        if (findByCode(code) != null) {
            database.deleteById(code);
        }
    }

    @Override
    public Url save(Url url) {
        return database.insert(url);
    }

    @Override
    public Url updateUrl(String code, Url url) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(code));

        Update update = new Update();
        update.set("destination", url.getDestination());
        update.set("enabled", url.isEnabled());
        update.set("expirationTime", url.getExpirationTime());

        mongoTemplate.findAndModify(query, update, Url.class);

        return findByCode(code);
    }

    @Override
    public Url findActive(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(code).and("enabled").is(true).and("expirationTime")
                .lt(System.currentTimeMillis()));
        return mongoTemplate.findOne(query, Url.class);
    }

}
