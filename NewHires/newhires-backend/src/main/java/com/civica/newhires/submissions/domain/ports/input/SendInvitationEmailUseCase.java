package com.civica.newhires.submissions.domain.ports.input;

import java.util.UUID;

public interface SendInvitationEmailUseCase {
    void send(UUID submissionId);
}