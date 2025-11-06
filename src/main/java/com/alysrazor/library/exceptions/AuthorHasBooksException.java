package com.alysrazor.library.exceptions;

public class AuthorHasBooksException extends RuntimeException {
    public AuthorHasBooksException(String message) {
        super(message);
    }

    public AuthorHasBooksException(int id) {
        super(
                String.format("Author with id %d has books!", id)
        );
    }
}
