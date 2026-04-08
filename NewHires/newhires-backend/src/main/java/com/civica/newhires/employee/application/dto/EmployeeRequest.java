package com.civica.newhires.employee.application.dto;

public record EmployeeRequest(
    String username,
    String password,
    String role
) {}