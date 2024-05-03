package io.houmlab.urlshortener.infrastructure.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.houmlab.urlshortener.model.Url;

public interface UrlMongoRepository extends MongoRepository<Url, String> {

}