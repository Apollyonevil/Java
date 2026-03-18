package com.civica.newhires.auth.infrastructure.adapters.output.identity;

import org.springframework.stereotype.Component;
import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;

import java.util.Optional;
import java.util.UUID;

@Component
public class MockUserIdentityAdapter implements UserIdentityPort {

    @Override
    public Optional<UUID> findEmployeeIdByToken(String token) {
        // Simulamos un ID de empleado fijo para pruebas
        return Optional.of(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Override
    public Optional<String> findEmailByToken(String token) {
        // Simulamos el email que recibirá las notificaciones en los tests
        return Optional.of("candidato-prueba@civica.com");
    }

    @Override
    public Optional<String> findNameByToken(String token) {
        // NUEVO: Simulamos el nombre del candidato
        // Esto es lo que aparecerá en el Admin Dashboard y en el saludo del Email
        return Optional.of("Candidato de Prueba Cívica");
    }

    @Override
    public void registerPendingInvite(String name, String email, String token) {
        // Como esto es un Mock, simplemente imprimimos en consola para saber que funciona
        System.out.println("DEBUG (Mock): Registrando invitación para " + name + " [" + email + "] con token: " + token);
}
}