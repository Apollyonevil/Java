package com.civica.newhires.submissions.infrastructure.adapters.output.listeners;

import com.civica.newhires.notification.domain.ports.input.NotifyCandidateUseCase;
import com.civica.newhires.submissions.domain.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubmissionsNotificationAdapter {

    private final NotifyCandidateUseCase notifyUseCase;

    @EventListener
    public void onApproved(SubmissionApprovedEvent event) {
        notifyUseCase.sendApprovalNotification(event.submissionId());
    }

    @EventListener
    public void onRejected(SubmissionRejectedEvent event) {
        notifyUseCase.sendRejectionNotification(
            event.submissionId(), 
            event.reason(), 
            event.newToken()
        );
    }

    @EventListener
    public void onRenewed(SubmissionRenewedEvent event) {
        notifyUseCase.sendInvitationNotification(event.submissionId(), event.token());
    }

    @EventListener
    public void onInvitationRequested(SubmissionInvitationEvent event) {
        notifyUseCase.sendInvitationNotification(event.submissionId(), event.token());
    }

    
}