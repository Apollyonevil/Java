package com.civica.newhires.submissions.domain.ports.input;

import com.civica.newhires.submissions.domain.model.Submission;
import java.util.UUID;

public interface RenewTokenUseCase {
    Submission execute(UUID id);
}