package com.civica.newhires.auth.application.service;

import com.civica.newhires.auth.domain.model.Invitation;
import com.civica.newhires.auth.domain.ports.input.ValidateAccessUseCase;
import com.civica.newhires.auth.domain.ports.output.InvitationRepository;

public class ValidateAccessService implements ValidateAccessUseCase {

    private final InvitationRepository repository;

    public ValidateAccessService(InvitationRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean execute(String token) {

        return repository.findByToken(token)
                .map(Invitation::isValid)
                .orElse(false);
    }
}