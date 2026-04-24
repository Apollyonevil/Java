package com.civica.newhires.notification.domain.ports.input;

import java.util.UUID;

public interface NotifyCandidateUseCase {
    void sendApprovalNotification(UUID submissionId);
    void sendRejectionNotification(UUID submissionId, String reason, String token);
    void sendInvitationNotification(UUID submissionId, String token);
}