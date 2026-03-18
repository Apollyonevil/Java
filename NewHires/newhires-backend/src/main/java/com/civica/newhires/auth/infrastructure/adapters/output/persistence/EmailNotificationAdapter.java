package com.civica.newhires.auth.infrastructure.adapters.output.persistence;

import com.civica.newhires.auth.domain.ports.output.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements NotificationService {

    @Override
    public void sendMagicLink(String email, String token) {
        System.out.println("🪄 [MAGIC LINK] Enviando acceso a: " + email + " | Token: " + token);
    }
}