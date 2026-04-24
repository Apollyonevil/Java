package com.civica.newhires.fields.domain.ports.input;

import com.civica.newhires.fields.domain.model.FieldDefinition;

public interface ManageFieldsUseCaseCreate {
    FieldDefinition createField(FieldDefinition field);

}