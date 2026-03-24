package com.civica.newhires.submissions.domain.model;

public enum SubmissionStatus {
    PENDING_INVITE,  // Invitación creada, link no usado aún
    SUBMITTED,       // El candidato ya envió el formulario
    PENDING_REVIEW,  // En revisión por RRHH
    COMPLETED,       // Proceso finalizado
    REJECTED         // Documentación rechazada por RRHH
}