package com.civica.newhires.submissions.domain.events;

import java.util.UUID;

public record SubmissionRenewedEvent(UUID submissionId, String token) {}