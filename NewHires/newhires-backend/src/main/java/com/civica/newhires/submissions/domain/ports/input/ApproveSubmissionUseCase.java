package com.civica.newhires.submissions.domain.ports.input;

import com.civica.newhires.submissions.domain.model.Submission;
import java.util.UUID;

public interface ApproveSubmissionUseCase {
    Submission execute(UUID id, String changedBy);
}