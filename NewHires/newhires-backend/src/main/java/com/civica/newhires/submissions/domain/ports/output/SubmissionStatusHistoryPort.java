package com.civica.newhires.submissions.domain.ports.output;

import com.civica.newhires.submissions.domain.model.SubmissionStatusHistory;
import java.util.List;
import java.util.UUID;

public interface SubmissionStatusHistoryPort {
    SubmissionStatusHistory save(SubmissionStatusHistory history);
    List<SubmissionStatusHistory> findAllBySubmissionId(UUID submissionId);
}