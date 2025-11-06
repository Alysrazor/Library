package com.alysrazor.library.exceptions;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String message) {
        super(message);
    }

    public PublisherNotFoundException(int id) {
        super(
                String.format("Publisher with id %d not found.", id)
        );
    }
}
