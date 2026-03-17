package com.civica.newhires.auth.infrastructure.adapters.output.persistence;

import com.civica.newhires.auth.domain.model.Invitation;
import com.civica.newhires.auth.domain.ports.output.InvitationRepository;
import com.civica.newhires.auth.infrastructure.adapters.output.persistence.mappers.InvitationPersistenceMapper;
import com.civica.newhires.auth.infrastructure.adapters.output.persistence.repository.JpaInvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InvitationPersistenceAdapter implements InvitationRepository {

    private final JpaInvitationRepository jpaRepository;
    private final InvitationPersistenceMapper mapper; // <--- Inyectamos el mapper

    @Override
    public void save(Invitation invitation) {
        jpaRepository.save(mapper.toEntity(invitation));
    }

    @Override
    public Optional<Invitation> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Invitation> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }
}