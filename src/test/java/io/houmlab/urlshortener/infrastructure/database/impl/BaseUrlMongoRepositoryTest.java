package io.houmlab.urlshortener.infrastructure.database.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import io.houmlab.urlshortener.infrastructure.database.UrlMongoRepository;
import io.houmlab.urlshortener.model.Url;
import io.houmlab.urlshortener.model.impl.BasicUrl;


class BaseUrlMongoRepositoryTest {

    @Mock
    private UrlMongoRepository database;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private BaseUrlMongoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByCode() {
        String code = "mockCode";
        Url url = new BasicUrl();
        url.setCode(code);
        url.setDestination("mockDestination");

        when(database.findById(code)).thenReturn(java.util.Optional.of(url));

        Url result = repository.findByCode(code);

        verify(database, times(1)).findById(code);
        assertEquals(url, result);
    }

    @Test
    void findActive() {
        String code = "mockCode";
        Url url = new BasicUrl();
        url.setCode(code);
        url.setEnabled(false);
        url.setExpirationTime(0);
        url.setDestination("mockDestination");

        when(mongoTemplate.findOne(any(), any())).thenReturn((url));

        Url result = repository.findActive(code);

        verify(mongoTemplate, times(1)).findOne(any(), any());
        assertEquals(url, result);
    }

    @Test
    void deleteByCode() {
        String code = "mockCode";
        Url url = new BasicUrl();
        url.setCode(code);
        url.setDestination("mockDestination");

        when(database.findById(code)).thenReturn(java.util.Optional.of(url));
        doNothing().when(database).deleteById(code);

        repository.deleteByCode(code);

        verify(database, times(1)).findById(code);
        verify(database, times(1)).deleteById(code);
    }

    @Test
    void save() {
        Url url = new BasicUrl();
        url.setCode("mockCode");
        url.setDestination("mockDestination");

        when(database.insert(url)).thenReturn(url);

        Url result = repository.save(url);

        assertEquals(url, result);
    }

    @Test
    void updateUrl() {
        String code = "mockCode";
        Url url = new BasicUrl();
        url.setCode(code);
        url.setDestination("mockDestination");

        when(database.findById(code)).thenReturn(java.util.Optional.of(url));
        when(database.save(url)).thenReturn(url);

        Url updatedUrl = repository.updateUrl(code, url);

        assertEquals(url, updatedUrl);
    }

}