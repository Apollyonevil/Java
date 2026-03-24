package com.civica.newhires.submissions.domain.ports.input;

import com.civica.newhires.submissions.domain.model.Submission;

public interface InviteCandidateUseCase {
    Submission execute(String name, String email);
}