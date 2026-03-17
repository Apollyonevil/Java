package com.civica.newhires.forms.domain.ports.output;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityPort {
    // Devuelve el ID del empleado asociado a un token válido
    Optional<UUID> findEmployeeIdByToken(String token);
}