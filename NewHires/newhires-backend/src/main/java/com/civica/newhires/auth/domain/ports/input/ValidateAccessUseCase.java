package com.civica.newhires.auth.domain.ports.input;

public interface ValidateAccessUseCase {

    boolean execute(String token);
}