package com.civica.newhires.employee.domain.ports.output;

public interface PasswordHasher {
    String hash(String raw);
}