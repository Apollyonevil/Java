package com.civica.newhires.auth.application.dto;

import java.io.Serializable;

public record EmployeeDTO(
    String id,
    String username,
    boolean enabled,
    String role 
) implements Serializable {}