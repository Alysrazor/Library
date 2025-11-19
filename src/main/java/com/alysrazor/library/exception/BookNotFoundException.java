package com.alysrazor.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(int id) {
        super(
                String.format("Book with id %d not found.", id)
        );
    }

    public BookNotFoundException(String isbn) {
        super(
                String.format("Book with ISBN: %s not found.", isbn)
        );
    }
}
