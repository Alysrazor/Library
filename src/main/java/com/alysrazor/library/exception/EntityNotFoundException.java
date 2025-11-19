package com.alysrazor.library.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() { }

    public EntityNotFoundException(Class<?> className, long id) {
        super(String.format(
                "%s not found: %d", className.getSimpleName(), id
        ));
    }

    public EntityNotFoundException(Class<?> className, String name) {
        super(String.format(
                "%s not found: %s", className.getSimpleName(), name
        ));
    }
}
