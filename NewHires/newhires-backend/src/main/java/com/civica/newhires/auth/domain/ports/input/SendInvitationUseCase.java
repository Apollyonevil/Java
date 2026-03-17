package com.civica.newhires.auth.domain.ports.input;

public interface SendInvitationUseCase {
    /**
     * Inicia el flujo de onboarding para un nuevo empleado
     * @param email Email personal del candidato
     */
    void execute(String email);
}