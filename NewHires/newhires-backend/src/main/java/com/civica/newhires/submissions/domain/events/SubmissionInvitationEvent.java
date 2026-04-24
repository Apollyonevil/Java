package com.civica.newhires.submissions.domain.events;

import java.util.UUID;

public record SubmissionInvitationEvent(UUID submissionId, String token) {}