package com.civica.newhires.forms.domain.ports.output;

public interface NotificationPort {
    void sendSubmissionConfirmation(String toEmail, String candidateName);
    void sendAdminNotification(String adminEmail, String candidateName);
    void sendRejectionNotice(String toEmail, String reason);
}