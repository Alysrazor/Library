package com.alysrazor.library.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
