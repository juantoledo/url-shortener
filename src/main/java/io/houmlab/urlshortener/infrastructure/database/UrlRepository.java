package io.houmlab.urlshortener.infrastructure.database;

import io.houmlab.urlshortener.model.Url;

public interface UrlRepository {

    Url findByCode(String code);

    Url findActive(String code);

    void deleteByCode(String code);

    Url save(Url url);

    Url updateUrl(String code, Url url);

}