package com.civica.newhires.candidates.domain.ports.input;

import com.civica.newhires.submissions.domain.model.Submission;
import java.util.UUID;

public interface InviteCandidateUseCase {
    Submission execute(String name, String email, UUID employeeId);
}