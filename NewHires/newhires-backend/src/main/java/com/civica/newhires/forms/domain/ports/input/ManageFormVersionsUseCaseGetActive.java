package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FormVersion;
import java.util.Optional;

public interface ManageFormVersionsUseCaseGetActive {
    Optional<FormVersion> getActiveVersion();

}