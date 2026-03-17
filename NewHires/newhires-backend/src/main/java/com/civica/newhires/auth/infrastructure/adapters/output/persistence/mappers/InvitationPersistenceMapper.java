package com.civica.newhires.auth.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.auth.domain.model.Invitation;
import com.civica.newhires.auth.infrastructure.adapters.output.persistence.entities.InvitationEntity;
import org.springframework.stereotype.Component;

@Component
public class InvitationPersistenceMapper {

    public InvitationEntity toEntity(Invitation domain) {
        if (domain == null) return null;
        
        InvitationEntity entity = new InvitationEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setToken(domain.getToken());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setStatus(domain.getStatus());
        return entity;
    }

    public Invitation toDomain(InvitationEntity entity) {
        if (entity == null) return null;

        return new Invitation(
            entity.getId(),
            entity.getEmail(),
            entity.getToken(),
            entity.getExpiresAt(),
            entity.getStatus()
        );
    }
}