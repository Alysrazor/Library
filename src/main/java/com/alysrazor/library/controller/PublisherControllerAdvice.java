package com.alysrazor.library.controller;

import com.alysrazor.library.exceptions.ErrorResponse;
import com.alysrazor.library.exceptions.PublisherHasBooksException;
import com.alysrazor.library.exceptions.PublisherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PublisherControllerAdvice {
    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePublisherNotFound(PublisherNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "Publisher not found.",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(PublisherHasBooksException.class)
    public ResponseEntity<ErrorResponse> handlePublisherHasBooks(PublisherHasBooksException ex) {
        ErrorResponse response = new ErrorResponse(
                "Can't delete publisher.",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
}
