package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import org.springframework.stereotype.Component;
import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;

import java.util.Optional;
import java.util.UUID;

@Component
public class MockUserIdentityAdapter implements UserIdentityPort {

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
        return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Override
    public Optional<String> findEmailByToken(String token) {
        return Optional.of("candidato-prueba@civica.com");
    }

    @Override
    public Optional<String> findNameByToken(String token) {
        return Optional.of("Candidato de Prueba Cívica");
    }

    @Override
    public void registerPendingInvite(String name, String email, String token) {
        System.out.println("DEBUG (Mock): Registrando invitación para " + name + " [" + email + "] con token: " + token);
}
}