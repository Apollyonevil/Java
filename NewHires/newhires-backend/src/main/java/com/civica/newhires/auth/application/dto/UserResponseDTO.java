package com.civica.newhires.auth.application.dto;

public record UserResponseDTO(
    String id,
    String username,
    String role
) {}