package com.civica.newhires.auth.domain.ports.input;

public interface ValidateAccessUseCase {
    /**
     * Verifica si un token es válido para permitir el acceso al formulario
     * @param token El identificador único recibido por email
     * @return true si el acceso es permitido
     */
    boolean execute(String token);
}