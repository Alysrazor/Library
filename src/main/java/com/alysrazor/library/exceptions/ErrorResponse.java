package com.alysrazor.library.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        String details,
        String message,
        LocalDateTime timestamp
) { }
