package com.alysrazor.library.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(int id) {
        super(
                String.format("Author with id %d not found.", id)
        );
    }
}
