package com.civica.newhires.fields.domain.ports.input;

import java.util.UUID;

public interface ManageFieldsUseCaseDelete {
    void deleteField(UUID id);
}