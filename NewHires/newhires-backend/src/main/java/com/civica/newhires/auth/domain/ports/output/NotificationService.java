package com.civica.newhires.auth.domain.ports.output;

public interface NotificationService {
    void sendMagicLink(String email, String token);
}