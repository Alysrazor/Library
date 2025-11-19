package com.alysrazor.library.controller;

import com.alysrazor.library.exception.AuthorHasBooksException;
import com.alysrazor.library.exception.AuthorNotFoundException;
import com.alysrazor.library.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AuthorControllerAdvice {
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFound(AuthorNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "Author not found.",
                ex.getMessage(),
                LocalDateTime.now()
        );

        System.out.println("Hola me he lanzado.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(AuthorHasBooksException.class)
    public ResponseEntity<ErrorResponse> handlePublisherHasBooks(AuthorHasBooksException ex) {
        ErrorResponse response = new ErrorResponse(
                "Can't delete author.",
                ex.getMessage(),
                LocalDateTime.now()
        );

        System.out.println("Hola me he lanzado.");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
}
