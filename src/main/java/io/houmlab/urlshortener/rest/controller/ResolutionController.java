package io.houmlab.urlshortener.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.houmlab.urlshortener.service.ResolutionService;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/")
public class ResolutionController {

    @Autowired
    private ResolutionService service;

    // @GetMapping("/")
    // public void home(HttpServletResponse response) throws Exception {
    //     response.sendRedirect( "/swagger-ui/index.html");
    // }

    @GetMapping("{code}")
    public void resolveUrl(@PathVariable String code, HttpServletResponse response) throws Exception {
        response.sendRedirect(service.resolveUrl(code));
    }
}

