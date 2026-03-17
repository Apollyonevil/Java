package com.civica.newhires.auth.application.dto;

public record InvitationRequest(String email) {
    // Usamos record para que sea inmutable y limpio
}