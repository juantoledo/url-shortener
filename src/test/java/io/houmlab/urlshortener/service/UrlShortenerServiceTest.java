package io.houmlab.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.houmlab.urlshortener.infrastructure.cache.UrlCache;
import io.houmlab.urlshortener.infrastructure.database.UrlRepository;
import io.houmlab.urlshortener.model.Url;
import io.houmlab.urlshortener.model.impl.BasicUrl;

class UrlShortenerServiceTest {

    @Mock
    private UrlRepository repository;

    @Mock
    private UrlCache cache;

    @InjectMocks
    private UrlShortenerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ObjectMapper mapper = new ObjectMapper();

    private static final String MOCK_URL_STRING = "{ \"destination\": \"https://www.example.com/\", \"enabled\": true, \"expirationTime\": 0}";

    @Test
    void createUrl() throws Exception {

        Url url = mapper.readValue(MOCK_URL_STRING, BasicUrl.class);

        when(repository.save(any(Url.class))).thenReturn(url);

        doNothing().when(cache).put(anyString(), anyString());

        Url result = service.createUrl(new BasicUrl());

        assertEquals(url, result);
    }

    @Test
    void getOriginalUrl() {
        Url url = new BasicUrl();
        when(repository.findByCode(anyString())).thenReturn(url);

        Url result = service.getOriginalUrl("mockCode");

        verify(repository, times(1)).findByCode(anyString());
        assertEquals(url, result);
    }

    @Test
    void deleteUrl() {
        doNothing().when(repository).deleteByCode(anyString());
        doNothing().when(cache).remove(anyString());

        service.deleteUrl("mockCode");

        verify(repository, times(1)).deleteByCode(anyString());
        verify(cache, times(1)).remove(anyString());
    }

    @Test
    void updateUrl() throws Exception {
        Url url = new BasicUrl();
        when(repository.updateUrl(anyString(), any(Url.class))).thenReturn(url);
        doNothing().when(cache).put(anyString(), anyString());

        Url result = service.updateUrl("mockCode", new BasicUrl());

        verify(repository, times(1)).updateUrl(anyString(), any(Url.class));
        verify(cache, times(1)).put(anyString(), anyString());
        assertEquals(url, result);
    }
}