package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import com.civica.newhires.forms.domain.ports.output.UserIdentityPort;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class MockUserIdentityAdapter implements UserIdentityPort {

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
        // Simulamos que el token siempre es válido y devuelve un UUID fijo
        // En el futuro, aquí validarás un JWT contra la base de datos
        return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }
}