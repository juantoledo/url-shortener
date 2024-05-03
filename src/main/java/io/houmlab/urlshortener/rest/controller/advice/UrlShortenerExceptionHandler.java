package io.houmlab.urlshortener.rest.controller.advice;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.mongodb.MongoException;

@ControllerAdvice
public class UrlShortenerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortenerExceptionHandler.class);

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> mediaTypeNotSupportedException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @ExceptionHandler({NoSuchElementException.class, NoResourceFoundException.class})
    public ResponseEntity<String> noSuchElementException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>("Http Resource not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>("Bad Http Request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<String> mongoException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allExceptions(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}