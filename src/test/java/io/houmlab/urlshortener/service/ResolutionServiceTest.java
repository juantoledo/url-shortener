package io.houmlab.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

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


class ResolutionServiceTest {

    @Mock
    private UrlRepository repository;

    @Mock
    private UrlCache cache;

    @InjectMocks
    private ResolutionService service;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String MOCK_URL_STRING = "{ \"destination\": \"https://www.example.com/\", \"enabled\": true, \"expirationTime\": 0}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void resolveUrl() throws Exception {
        String code = "mockCode";

        Url url = mapper.readValue(MOCK_URL_STRING, BasicUrl.class);

        when(cache.get(code)).thenReturn(mapper.writeValueAsString(url));
        url.setCode(code);
        url.setDestination("mockDestination");

        when(cache.get(code)).thenReturn(null);
        when(repository.findActive(code)).thenReturn(url);
        doNothing().when(cache).put(code, mapper.writeValueAsString(url));

        String result = service.resolveUrl(code);

        assertEquals(url.getDestination(), result);
    }

    @Test
    void resolveUrlWithValidCachedElement() throws Exception {
        String code = "mockCode";

        Url url = mapper.readValue(MOCK_URL_STRING, BasicUrl.class);

        when(cache.get(code)).thenReturn(MOCK_URL_STRING);

        url.setCode(code);
        url.setDestination("https://www.example.com/");

        doNothing().when(cache).put(code, mapper.writeValueAsString(url));

        String result = service.resolveUrl(code);

        assertEquals(url.getDestination(), result);
    }

    @Test
    void resolveUrlWhenNoCachedElementAndNotInDatabaseEither() throws Exception {
        String code = "mockCode";

        Url url = mapper.readValue(MOCK_URL_STRING, BasicUrl.class);

        when(cache.get(code)).thenReturn(mapper.writeValueAsString(url));
        url.setCode(code);
        url.setDestination("mockDestination");

        when(cache.get(code)).thenReturn(null);

        when(repository.findActive(code)).thenReturn(null);

        doNothing().when(cache).put(code, mapper.writeValueAsString(url));

        assertThrows(NoSuchElementException.class, () -> service.resolveUrl(code));
    }
}