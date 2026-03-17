package com.civica.newhires.auth.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.auth.infrastructure.adapters.output.persistence.entities.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface JpaInvitationRepository extends JpaRepository<InvitationEntity, UUID> {
    Optional<InvitationEntity> findByToken(String token);
    Optional<InvitationEntity> findByEmail(String email);
}