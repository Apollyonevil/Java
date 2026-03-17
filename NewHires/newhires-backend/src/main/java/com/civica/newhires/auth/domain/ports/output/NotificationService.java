package com.civica.newhires.auth.domain.ports.output;

public interface NotificationService {
    // El dominio solo dice: "Envía esto", no sabe cómo se formatea el HTML ni el servidor SMTP
    void sendMagicLink(String email, String token);
}