package com.civica.newhires.auth.domain.ports.input;

public interface SendInvitationUseCase {

    void execute(String email);
}