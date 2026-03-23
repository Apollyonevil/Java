package com.civica.newhires.forms.domain.ports.output;

import com.civica.newhires.forms.domain.model.FormVersion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FormVersionRepository {
    FormVersion save(FormVersion version);
    Optional<FormVersion> findById(UUID id);
    Optional<FormVersion> findActive();
    List<FormVersion> findAll();
    void deactivateAll();
    void deleteById(UUID versionId);
}