package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.Submission;
import java.util.List;

public interface GetSubmissionsUseCase {
    List<Submission> execute();
}