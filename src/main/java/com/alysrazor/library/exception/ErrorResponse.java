package com.alysrazor.library.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String details,
        String message,
        LocalDateTime timestamp
) { }
