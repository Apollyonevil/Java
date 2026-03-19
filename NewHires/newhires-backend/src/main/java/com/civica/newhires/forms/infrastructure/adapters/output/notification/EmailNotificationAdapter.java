package com.civica.newhires.forms.infrastructure.adapters.output.notification;

import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("formsEmailNotificationAdapter")
@RequiredArgsConstructor
public class EmailNotificationAdapter implements NotificationPort {

    private final JavaMailSender mailSender;

    @Override
    public void sendSubmissionConfirmation(String toEmail, String candidateName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("rrhh@civica.com"); 
        message.setSubject("¡Formulario Recibido - Cívica!");
        message.setText("Hola " + candidateName + ",\nHemos recibido tu documentación correctamente. RRHH la revisará pronto.");
        mailSender.send(message);
    }

    @Override
    public void sendInvitation(String toEmail, String candidateName, String token) {
        String link = "http://localhost:4200/onboarding?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("rrhh@civica.com");
        message.setSubject("Bienvenido a Cívica - Completa tu documentación");
        message.setText("Hola " + candidateName + ",\n\nTe damos la bienvenida a Cívica. Por favor completa tu documentación en el siguiente enlace:\n\n" + link + "\n\nEl enlace caduca en 48 horas.");
        mailSender.send(message);
    }

    @Override
    public void sendAdminNotification(String adminEmail, String candidateName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setFrom("sistema@civica.com");
        message.setSubject("Nueva Incorporación: " + candidateName);
        message.setText("El candidato " + candidateName + " ha finalizado el formulario de onboarding.");
        mailSender.send(message);
    }

    @Override
    public void sendRejectionNotice(String toEmail, String reason) {
    }
}