package com.civica.newhires.auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public enum InvitationStatus {
    PENDING,    // Invitación enviada, esperando al empleado
    USED,       // El empleado ya ha accedido y completado su parte
    EXPIRED     // Se pasó el plazo de 48 horas
}