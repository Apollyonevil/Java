package com.civica.newhires.submissions.application.service;

import com.civica.newhires.candidates.domain.model.AccessToken;
import com.civica.newhires.candidates.domain.ports.output.AccessTokenPort;
import com.civica.newhires.submissions.domain.events.*;
import com.civica.newhires.submissions.domain.model.*;
import com.civica.newhires.submissions.domain.ports.input.*;
import com.civica.newhires.submissions.domain.ports.output.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmissionsAdminService implements 
    ApproveSubmissionUseCase, 
    RejectSubmissionUseCase, 
    RenewTokenUseCase, 
    SendInvitationEmailUseCase {
    
    private final SubmissionPort submissionRepository;
    private final AccessTokenPort accessTokenRepository;
    private final SubmissionStatusHistoryPort historyRepository;
    private final ApplicationEventPublisher eventPublisher;
    
    @PersistenceContext
    private EntityManager entityManager;

    // ApproveSubmissionUseCase
    @Override
    @Transactional
    public Submission execute(UUID id, String changedBy) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission no encontrada: " + id));
        
        submission.setStatus(SubmissionStatus.APPROVED);
        submissionRepository.save(submission);
        
        historyRepository.save(new SubmissionStatusHistory(id, SubmissionStatus.APPROVED, changedBy));
        
        eventPublisher.publishEvent(new SubmissionApprovedEvent(id));
        return submission;
    }

    // RejectSubmissionUseCase
    @Override
    @Transactional
    public Submission execute(UUID id, String reason, String changedBy) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado: " + id));
        
        accessTokenRepository.deleteBySubmissionId(id);
        entityManager.flush(); 

        submission.setStatus(SubmissionStatus.REJECTED);
        submissionRepository.save(submission);

        AccessToken token = new AccessToken(id);
        accessTokenRepository.save(token);
        
        historyRepository.save(new SubmissionStatusHistory(id, SubmissionStatus.REJECTED, changedBy));

        eventPublisher.publishEvent(new SubmissionRejectedEvent(id, reason, token.getToken()));
        return submission;
    }

    // RenewTokenUseCase
    @Override
    @Transactional
    public Submission execute(UUID id) { 
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado: " + id));

        accessTokenRepository.deleteBySubmissionId(id);
        entityManager.flush();
        
        AccessToken newToken = new AccessToken(id);
        accessTokenRepository.save(newToken);
        
        eventPublisher.publishEvent(new SubmissionRenewedEvent(id, newToken.getToken()));
        return submission;
    }

    // SendInvitationEmailUseCase (Asegúrate de que esta interfaz devuelva VOID)
    @Override
    public void send(UUID id) { 
        AccessToken token = accessTokenRepository.findValidBySubmissionId(id)
                .orElseThrow(() -> new RuntimeException("No hay token activo"));
        
        eventPublisher.publishEvent(new SubmissionInvitationEvent(id, token.getToken()));
    }
}