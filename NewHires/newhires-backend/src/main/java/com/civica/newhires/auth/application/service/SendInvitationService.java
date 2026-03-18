package com.civica.newhires.auth.application.service;

import com.civica.newhires.auth.domain.model.Invitation;
import com.civica.newhires.auth.domain.ports.input.SendInvitationUseCase;
import com.civica.newhires.auth.domain.ports.output.InvitationRepository;
import com.civica.newhires.auth.domain.ports.output.NotificationService;

public class SendInvitationService implements SendInvitationUseCase {

    private final InvitationRepository repository;
    private final NotificationService notificationService;

    public SendInvitationService(InvitationRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public void execute(String email) {

        Invitation invitation = new Invitation(email);
        repository.save(invitation);
        notificationService.sendMagicLink(invitation.getEmail(), invitation.getToken());
    }
}