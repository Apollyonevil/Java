package com.civica.newhires.notification.domain.ports.output;

public interface NotificationPort {
    void sendSubmissionConfirmation(String toEmail, String candidateName);
    void sendAdminNotification(String adminEmail, String candidateName);
    void sendApprovalNotice(String toEmail, String candidateName);
    void sendRejectionNotice(String toEmail, String reason);
    void sendInvitation(String toEmail, String candidateName, String token); 
}