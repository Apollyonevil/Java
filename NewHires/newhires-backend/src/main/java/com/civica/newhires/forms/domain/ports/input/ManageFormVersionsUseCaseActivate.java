package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FormVersion;
import java.util.UUID;

public interface ManageFormVersionsUseCaseActivate {
    FormVersion activateVersion(UUID versionId);
}