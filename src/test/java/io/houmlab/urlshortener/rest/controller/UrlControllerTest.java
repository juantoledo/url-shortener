package io.houmlab.urlshortener.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.houmlab.urlshortener.service.UrlShortenerService;


@SpringBootTest
@AutoConfigureMockMvc
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService service;

    private static final String MOCK_URL_STRING = "{ \"destination\": \"https://www.example.com/\", \"enabled\": true, \"expirationTime\": 0}";

    @Test
    void createUrl() throws Exception {
        mockMvc.perform(post("/admin/url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MOCK_URL_STRING))
                .andExpect(status().isOk());
    }

    @Test
    void createUrlBadRequest() throws Exception {
        mockMvc.perform(post("/admin/url")
                .contentType(MediaType.APPLICATION_JSON)
                .content("bad json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUrl() throws Exception {
        mockMvc.perform(get("/admin/url/{code}", "mockCode")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateUrl() throws Exception {
        mockMvc.perform(put("/admin/url/{code}", "mockCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"http://newmockurl.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUrl() throws Exception {
        mockMvc.perform(delete("/admin/url/{code}", "mockCode"))
                .andExpect(status().isOk())
                .andExpect(content().string("URL deleted successfully!"));
    }
}