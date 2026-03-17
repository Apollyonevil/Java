package com.civica.newhires.shared.infrastructure.rest;

import java.time.LocalDateTime;

public record ErrorMessage(
    int statusCode,
    LocalDateTime timestamp,
    String message,
    String description
) {}