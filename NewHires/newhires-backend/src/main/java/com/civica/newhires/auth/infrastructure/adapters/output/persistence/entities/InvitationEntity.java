package com.civica.newhires.auth.infrastructure.adapters.output.persistence.entities;

import com.civica.newhires.auth.domain.model.InvitationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitations")
@Getter
@Setter
public class InvitationEntity {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String token;

    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
}