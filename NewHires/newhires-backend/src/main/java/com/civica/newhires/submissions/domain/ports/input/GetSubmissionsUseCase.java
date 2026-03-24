package com.civica.newhires.submissions.domain.ports.input;

import java.util.List;

import com.civica.newhires.submissions.domain.model.Submission;

public interface GetSubmissionsUseCase {
    List<Submission> execute();
}