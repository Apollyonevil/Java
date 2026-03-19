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
        // 1. Guardamos la invitación en BD (esto es lo importante)
        Invitation invitation = new Invitation(email);
        repository.save(invitation);

        // 2. Intentamos enviar el mail, pero que no rompa el flujo si falla
        try {
            notificationService.sendMagicLink(invitation.getEmail(), invitation.getToken());
        } catch (Exception e) {
            // Logueamos el error para saber qué pasó (ej. el límite de Mailtrap)
            // Pero NO relanzamos la excepción para que el frontend reciba un OK (200)
            System.err.println("Error enviando invitación por email: " + e.getMessage());
            // Aquí podrías marcar la invitación en BD como "PENDIENTE_ENVIO" si quisieras
        }
    }
}