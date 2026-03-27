package com.civica.newhires.auth.application.dto;

public record AdminUserRequest(
    String username,
    String password
) {}