package io.houmlab.urlshortener.infrastructure.cache;

public interface UrlCache {

    void put(String key, String value);

    String get(String key);

    void remove(String key);

    boolean containsKey(String key);
}
