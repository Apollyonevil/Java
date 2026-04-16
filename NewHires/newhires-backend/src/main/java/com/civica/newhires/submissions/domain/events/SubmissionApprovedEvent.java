package com.civica.newhires.submissions.domain.events;

import java.util.UUID;

public record SubmissionApprovedEvent(UUID submissionId) {}