package com.civica.newhires.forms.domain.ports.input;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import java.util.UUID;

public interface ManageFieldsUseCaseCreate {
    FieldDefinition createField(FieldDefinition field);

}