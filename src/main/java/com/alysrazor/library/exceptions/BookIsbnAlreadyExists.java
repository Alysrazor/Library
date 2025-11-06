package com.alysrazor.library.exceptions;

public class BookIsbnAlreadyExists extends RuntimeException {

    public BookIsbnAlreadyExists(String isbn) {
        super(
                String.format("A book with the following ISBN: " +
                                "%s already exists.",
                        isbn)
        );
    }
}
