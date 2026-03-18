package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.Submission;

public interface InviteCandidateUseCase {
    Submission execute(String name, String email);
}