package com.civica.newhires.employee.application.dto;

import java.io.Serializable;

public record EmployeeDTO(
    String id,
    String username,
    boolean enabled,
    String role 
) implements Serializable {}