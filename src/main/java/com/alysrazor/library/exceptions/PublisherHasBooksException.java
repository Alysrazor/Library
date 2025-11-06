package com.alysrazor.library.exceptions;

public class PublisherHasBooksException extends RuntimeException {
    public PublisherHasBooksException(String message) {
        super(message);
    }

    public PublisherHasBooksException(int id) {
        super(
                String.format("Publisher with id %d has books!", id)
        );
    }
}
