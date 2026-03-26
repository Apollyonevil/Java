package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FormVersion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManageFormVersionsUseCaseDelete {
    void deleteVersion(UUID versionId);
}