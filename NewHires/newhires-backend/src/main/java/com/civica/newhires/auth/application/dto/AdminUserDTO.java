package com.civica.newhires.auth.application.dto;

import java.io.Serializable;

public record AdminUserDTO(
    String id,
    String username,
    boolean enabled
) implements Serializable {}

// ---

