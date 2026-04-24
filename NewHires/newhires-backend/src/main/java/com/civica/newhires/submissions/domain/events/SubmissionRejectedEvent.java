package com.civica.newhires.submissions.domain.events;

import java.util.UUID;

public record SubmissionRejectedEvent(UUID submissionId, String reason, String newToken) {}