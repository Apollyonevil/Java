package com.civica.newhires.notification.application.service;

import com.civica.newhires.notification.domain.ports.input.NotifyCandidateUseCase;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import com.civica.newhires.submissions.domain.ports.output.SubmissionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotifyCandidateService implements NotifyCandidateUseCase {

    private final NotificationPort notificationPort;
    private final SubmissionPort submissionPort;

    @Override
    public void sendApprovalNotification(UUID submissionId) {
        var sub = submissionPort.findById(submissionId).orElseThrow();
        notificationPort.sendApprovalNotice(sub.getEmail(), sub.getCandidateName());
    }

    @Override
    public void sendRejectionNotification(UUID submissionId, String reason, String token) {
        var sub = submissionPort.findById(submissionId).orElseThrow();
        notificationPort.sendRejectionNotice(sub.getEmail(), reason);
        notificationPort.sendInvitation(sub.getEmail(), sub.getCandidateName(), token);
    }

    @Override
    public void sendInvitationNotification(UUID submissionId, String token) {
        var sub = submissionPort.findById(submissionId).orElseThrow();
        notificationPort.sendInvitation(sub.getEmail(), sub.getCandidateName(), token);
    }
}