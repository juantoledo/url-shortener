package io.houmlab.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.houmlab.urlshortener.infrastructure.cache.UrlCache;
import io.houmlab.urlshortener.infrastructure.database.UrlRepository;
import io.houmlab.urlshortener.model.Url;
import io.houmlab.urlshortener.utils.UrlUtils;


@Service
public class UrlShortenerService {

    private UrlRepository repository;
    private UrlCache cache;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UrlShortenerService(UrlRepository repository, UrlCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    public Url createUrl(Url url) throws Exception {
        url.setCode(UrlUtils.generateCode());
        Url savedUrl = repository.save(url);
        cache.put(savedUrl.getCode(), mapper.writeValueAsString(savedUrl));
        return savedUrl;
    }

    public Url getOriginalUrl(String code) {
        return repository.findByCode(code);
    }

    public void deleteUrl(String code) {
        cache.remove(code);
        repository.deleteByCode(code);

    }

    public Url updateUrl(String code, Url url) throws Exception {
        Url updatedUrl = repository.updateUrl(code, url);
        cache.put(code, mapper.writeValueAsString(updatedUrl));
        return updatedUrl;
    }

}
