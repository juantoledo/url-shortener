package io.houmlab.urlshortener.rest.controller;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.houmlab.urlshortener.service.ResolutionService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class ResolutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResolutionService service;

    @BeforeEach
    void setUp() {
        try {
            when(service.resolveUrl(anyString())).thenReturn("http://www.example.com");
        } catch (Exception e) {
            
        }
    }

    @Test
    void resolveUrl() throws Exception {
        mockMvc.perform(get("/{code}", "mockCode")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://www.example.com"));

        verify(service, times(1)).resolveUrl("mockCode");
    }
}