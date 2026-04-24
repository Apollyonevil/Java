package com.civica.newhires.notification.infrastructure.listeners;

import com.civica.newhires.candidates.domain.events.CandidateInvitedEvent;
import com.civica.newhires.notification.domain.ports.output.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidateInvitedEventListener {

    private final NotificationPort notificationPort;

    @EventListener
    public void onCandidateInvited(CandidateInvitedEvent event) {
        notificationPort.sendInvitation(event.getEmail(), event.getName(), event.getToken());
    }
}