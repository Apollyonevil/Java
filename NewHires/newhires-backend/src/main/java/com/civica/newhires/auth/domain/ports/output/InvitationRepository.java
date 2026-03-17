package com.civica.newhires.auth.domain.ports.output;

import com.civica.newhires.auth.domain.model.Invitation;
import java.util.Optional;

public interface InvitationRepository {
    void save(Invitation invitation);
    Optional<Invitation> findByToken(String token);
    Optional<Invitation> findByEmail(String email);
}