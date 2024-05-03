package io.houmlab.urlshortener.service;

import java.util.NoSuchElementException;
import static java.util.Objects.isNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.houmlab.urlshortener.infrastructure.cache.UrlCache;
import io.houmlab.urlshortener.infrastructure.database.UrlRepository;
import io.houmlab.urlshortener.model.Url;
import io.houmlab.urlshortener.model.impl.BasicUrl;


@Service
public class ResolutionService {

    private UrlRepository repository;
    private UrlCache cache;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ResolutionService(UrlRepository repository, UrlCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    public String resolveUrl(String code) throws Exception {

        Url url = getActiveUrl(code);

        if (isNull(url)) { 
            throw new NoSuchElementException();
        }   
        return getActiveUrl(code).getDestination();
    }

    private Url getActiveUrl(String code) throws Exception {
        Url url = null;

        if (cache.get(code) == null) {
            url = repository.findActive(code);
            if (url != null) {
                cache.put(url.getCode(), mapper.writeValueAsString(url));
                return url;
            }
            throw new NoSuchElementException();
        } else {
            url = mapper.readValue(cache.get(code), BasicUrl.class);
        }

        return url.isEnabled() ? url : null;

    }

}
