package com.civica.newhires.employee.domain.ports.output;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityPort {
    Optional<UUID> findEmployeeIdByToken(String token);
    Optional<String> findNameByToken(String token);
    Optional<String> findEmailByToken(String token); 
    void registerPendingInvite(String name, String email, String token);
}