package com.civica.newhires.auth.application.dto;

public record EmployeeRequest(
    String username,
    String password,
    String role
) {}