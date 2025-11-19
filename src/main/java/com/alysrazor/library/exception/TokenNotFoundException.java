package com.alysrazor.library.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(Long id) {
        super(
                String.format("Token with Id %d not found.", id)
        );
    }
}
