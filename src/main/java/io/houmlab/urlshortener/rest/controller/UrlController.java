package io.houmlab.urlshortener.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.houmlab.urlshortener.model.Url;
import io.houmlab.urlshortener.model.impl.BasicUrl;
import io.houmlab.urlshortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/url")
public class UrlController {

    @Autowired
    private UrlShortenerService service;

    @Operation(summary = "Create a new URL")
    @PostMapping(path = "", consumes = "application/json")
    public Url createUrl(@Valid @RequestBody BasicUrl url) throws Exception {
        return service.createUrl(url);
    }

    @Operation(summary = "Get an URL")
    @GetMapping(path = "/{code}")
    public Url getUrl(@PathVariable String code) {
        return service.getOriginalUrl(code);
    }

    @Operation(summary = "Update an URL")
    @PutMapping(path = "/{code}", consumes = "application/json")
    public Url updateUrl(@PathVariable String code, @RequestBody BasicUrl newUrl) throws Exception {
        return service.updateUrl(code, newUrl);

    }

    @Operation(summary = "Delete an URL")
    @DeleteMapping(path = "/{code}")
    public String deleteUrl(@PathVariable String code) {
        service.deleteUrl(code);
        return "URL deleted successfully!";
    }
}