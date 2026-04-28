package com.civica.newhires.employee.infrastructure.adapters.output.identity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.civica.newhires.employee.domain.ports.output.PasswordHasher;

@Component
public class BcryptPasswordHasher implements PasswordHasher {
    private final PasswordEncoder encoder;
    public BcryptPasswordHasher(PasswordEncoder encoder) { this.encoder = encoder; }
    public String hash(String raw) { return encoder.encode(raw); }
}