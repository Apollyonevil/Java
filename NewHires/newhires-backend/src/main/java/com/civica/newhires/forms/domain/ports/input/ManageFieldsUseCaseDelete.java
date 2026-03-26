package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import java.util.UUID;

public interface ManageFieldsUseCaseDelete {
    void deleteField(UUID id);
}