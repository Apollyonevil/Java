package com.civica.newhires.forms.infrastructure.adapters.output.notification;

import com.civica.newhires.forms.domain.ports.output.NotificationPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("formsEmailNotificationAdapter")
@RequiredArgsConstructor
public class EmailNotificationAdapter implements NotificationPort {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private static final String ORANGE = "#ff5038";
    private static final String DARK = "#343a40";
    private static final String GRAY = "#6c757d";
    private static final String LIGHT = "#f8f9fa";

    private String buildEmail(String title, String body) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin:0; padding:0; background-color:#f0f0f0; font-family: Arial, sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f0f0f0; padding: 40px 0;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:8px; overflow:hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                                <!-- Header -->
                                <tr>
                                    <td style="background-color:%s; padding: 30px 40px; text-align:center;">
                                        <h1 style="color:#ffffff; margin:0; font-size:24px; font-weight:700; letter-spacing:1px;">Cívica</h1>
                                        <p style="color:rgba(255,255,255,0.85); margin:8px 0 0 0; font-size:13px;">%s</p>
                                    </td>
                                </tr>
                                <!-- Body -->
                                <tr>
                                    <td style="padding: 40px;">
                                        %s
                                    </td>
                                </tr>
                                <!-- Footer -->
                                <tr>
                                    <td style="background-color:%s; padding: 20px 40px; text-align:center;">
                                        <p style="color:%s; margin:0; font-size:12px;">© 2026 Cívica · Este es un mensaje automático, por favor no respondas a este email.</p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """.formatted(ORANGE, title, body, LIGHT, GRAY);
    }

    private void sendHtmlEmail(String to, String subject, String title, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setFrom("rrhh@civica.com");
            helper.setSubject(subject);
            helper.setText(buildEmail(title, body), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviando email", e);
        }
    }

    @Override
    public void sendInvitation(String toEmail, String candidateName, String token) {
        String link = frontendUrl + "/onboarding?token=" + token;
        String body = """
            <h2 style="color:%s; margin:0 0 16px 0; font-size:20px;">¡Bienvenido/a, %s!</h2>
            <p style="color:%s; line-height:1.6; margin:0 0 24px 0;">
                Te damos la bienvenida a Cívica. Para completar tu proceso de incorporación
                necesitamos que rellenes tu documentación a través del siguiente enlace.
            </p>
            <table width="100%%" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" style="padding: 24px 0;">
                        <a href="%s" style="background-color:%s; color:#ffffff; padding:14px 32px; text-decoration:none; border-radius:6px; font-weight:700; font-size:15px; display:inline-block;">
                            Completar Documentación →
                        </a>
                    </td>
                </tr>
            </table>
            <p style="color:%s; font-size:13px; margin:0; text-align:center;">
                Este enlace caduca en 48 horas.<br>
                Si el botón no funciona, copia y pega este enlace en tu navegador:<br>
                <span style="color:%s; word-break:break-all;">%s</span>
            </p>
            """.formatted(DARK, candidateName, GRAY, link, ORANGE, GRAY, ORANGE, link);

        sendHtmlEmail(toEmail, "Bienvenido/a a Cívica - Completa tu documentación", "Proceso de Incorporación", body);
    }

    @Override
    public void sendSubmissionConfirmation(String toEmail, String candidateName) {
        String body = """
            <h2 style="color:%s; margin:0 0 16px 0; font-size:20px;">¡Documentación recibida!</h2>
            <p style="color:%s; line-height:1.6; margin:0 0 24px 0;">
                Hola <strong>%s</strong>, hemos recibido tu documentación correctamente.
                El equipo de RRHH la revisará en los próximos días y te informaremos del resultado.
            </p>
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:%s; border-radius:6px; margin-bottom:24px;">
                <tr>
                    <td style="padding:20px 24px;">
                        <p style="margin:0; color:%s; font-size:14px;">
                            ✅ Documentación enviada correctamente<br>
                            ⏳ Pendiente de revisión por RRHH
                        </p>
                    </td>
                </tr>
            </table>
            <p style="color:%s; font-size:13px; margin:0;">
                Si tienes alguna duda, contacta con el equipo de RRHH.
            </p>
            """.formatted(DARK, GRAY, candidateName, LIGHT, DARK, GRAY);

        sendHtmlEmail(toEmail, "¡Formulario Recibido - Cívica!", "Confirmación de Envío", body);
    }

    @Override
    public void sendAdminNotification(String adminEmail, String candidateName) {
        String body = """
            <h2 style="color:%s; margin:0 0 16px 0; font-size:20px;">Nueva incorporación lista para revisar</h2>
            <p style="color:%s; line-height:1.6; margin:0 0 24px 0;">
                El candidato <strong>%s</strong> ha completado su formulario de onboarding
                y está pendiente de revisión.
            </p>
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:%s; border-radius:6px; margin-bottom:24px;">
                <tr>
                    <td style="padding:20px 24px;">
                        <p style="margin:0; color:%s; font-size:14px;">
                            👤 Candidato: <strong>%s</strong><br>
                            📋 Estado: Pendiente de revisión
                        </p>
                    </td>
                </tr>
            </table>
            <p style="color:%s; font-size:13px; margin:0;">
                Accede al panel de administración para revisar la documentación.
            </p>
            """.formatted(DARK, GRAY, candidateName, LIGHT, DARK, candidateName, GRAY);

        sendHtmlEmail(adminEmail, "Nueva Incorporación: " + candidateName, "Panel de Administración", body);
    }

    @Override
    public void sendRejectionNotice(String toEmail, String reason) {
        String body = """
            <h2 style="color:#dc3545; margin:0 0 16px 0; font-size:20px;">Documentación rechazada</h2>
            <p style="color:%s; line-height:1.6; margin:0 0 24px 0;">
                Tu documentación ha sido revisada y necesita correcciones antes de poder continuar
                con el proceso de incorporación.
            </p>
            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#fff5f5; border-left:4px solid #dc3545; border-radius:0 6px 6px 0; margin-bottom:24px;">
                <tr>
                    <td style="padding:20px 24px;">
                        <p style="margin:0 0 8px 0; color:#dc3545; font-weight:700; font-size:13px; text-transform:uppercase;">Motivo del rechazo</p>
                        <p style="margin:0; color:%s; font-size:14px; line-height:1.6;">%s</p>
                    </td>
                </tr>
            </table>
            <p style="color:%s; line-height:1.6; margin:0 0 8px 0;">
                Recibirás un segundo email con un nuevo enlace para corregir y reenviar tu documentación.
            </p>
            <p style="color:%s; font-size:13px; margin:0;">
                Si tienes alguna duda sobre los motivos del rechazo, contacta con el equipo de RRHH.
            </p>
            """.formatted(GRAY, DARK, reason, GRAY, GRAY);

        sendHtmlEmail(toEmail, "Documentación rechazada - Cívica", "Revisión de Documentación", body);
    }
}