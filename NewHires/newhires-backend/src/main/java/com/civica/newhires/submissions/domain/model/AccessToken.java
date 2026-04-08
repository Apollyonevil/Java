package com.civica.newhires.submissions.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class AccessToken {
    private final UUID submissionId;
    private final String token;
    private final LocalDateTime expiresAt;
    private boolean used; // Quitamos el final para poder marcarlo como usado

    // Constructor completo para el Mapper/Adapter
    public AccessToken(UUID submissionId, String token, LocalDateTime expiresAt, boolean used) {
        this.submissionId = submissionId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    // Constructor de conveniencia para nuevos tokens
    public AccessToken(UUID submissionId) {
        this.submissionId = submissionId;
        this.token = UUID.randomUUID().toString();
        this.expiresAt = LocalDateTime.now().plusHours(48);
        this.used = false;
    }

    // --- MÉTODOS QUE SOLUCIONAN TU ERROR ---

    /**
     * Verifica si el token no ha sido usado y no ha expirado.
     */
    public boolean isValid() {
        return !used && (expiresAt == null || LocalDateTime.now().isBefore(expiresAt));
    }

    /**
     * Cambia el estado del token a usado.
     */
    public void markAsUsed() {
        this.used = true;
    }
}