package com.civica.newhires.forms.infrastructure.adapters.output.notification;

import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("formsEmailNotificationAdapter") // <--- AÑADE ESTE NOMBRE AQUÍ
@RequiredArgsConstructor
public class EmailNotificationAdapter implements NotificationPort {

    private final JavaMailSender mailSender;

    @Override
    public void sendSubmissionConfirmation(String toEmail, String candidateName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("rrhh@civica.com"); // Es buena práctica poner el remitente
        message.setSubject("¡Formulario Recibido - Cívica!");
        message.setText("Hola " + candidateName + ",\nHemos recibido tu documentación correctamente. RRHH la revisará pronto.");
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
        // Lógica para el caso de rechazo
    }
}