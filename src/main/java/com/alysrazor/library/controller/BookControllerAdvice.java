package com.alysrazor.library.controller;

import com.alysrazor.library.exception.BookIsbnAlreadyExists;
import com.alysrazor.library.exception.BookNotFoundException;
import com.alysrazor.library.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "Book not found.",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(BookIsbnAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleBookIsbnAlreadyExists(BookIsbnAlreadyExists ex) {
        ErrorResponse response = new ErrorResponse(
                "ISBN already exists.",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
}
