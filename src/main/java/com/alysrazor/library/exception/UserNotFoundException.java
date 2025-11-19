package com.alysrazor.library.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super(
                String.format("User with Id %d not found.", id)
        );
    }
}
