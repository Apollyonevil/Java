package com.civica.newhires.forms.application.service;

import com.civica.newhires.auth.domain.ports.output.UserIdentityPort;
import com.civica.newhires.forms.domain.model.Submission;
import com.civica.newhires.forms.domain.model.SubmissionStatus; // Importante el Enum
import com.civica.newhires.forms.domain.ports.input.InviteCandidateUseCase;
import com.civica.newhires.forms.domain.ports.output.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteCandidateService implements InviteCandidateUseCase {

    private final SubmissionRepository submissionRepository;
    private final UserIdentityPort userIdentityPort;

    @Override
    @Transactional
    public Submission execute(String name, String email) {
        // 1. Generamos el Token (String) y el ID del empleado (UUID)
        String token = UUID.randomUUID().toString();
        UUID employeeId = UUID.randomUUID(); 

        // 2. Registramos en el puerto de identidad
        // Nota: Asegúrate de haber añadido este método a la interfaz UserIdentityPort
        userIdentityPort.registerPendingInvite(name, email, token);

        // 3. Creamos el registro de Submission inicial
        // Usamos el nuevo constructor que incluye el TOKEN para evitar el error de "undefined setToken"
        Submission submission = new Submission(
            employeeId, 
            name, 
            email, 
            token
        );
        
        // El status ya se pone como PENDING_INVITE dentro del constructor que creamos,
        // pero si necesitas forzarlo a otro valor del Enum:
        submission.setStatus(SubmissionStatus.PENDING_INVITE);

        // 4. Persistencia
        submissionRepository.save(submission);
        
        return submission;
    }
}