package com.example.project_module5.controller;

import com.example.project_module5.exception.IllegalDateException;
import com.example.project_module5.exception.TickerNotFoundException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(TickerNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(TickerNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(IllegalDateException.class)
    public ResponseEntity<ErrorMessage> IllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorMessage> IllegalArgumentException(HttpClientErrorException.NotFound exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
