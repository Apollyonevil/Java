package com.civica.newhires.fields.domain.ports.input;

import com.civica.newhires.fields.domain.model.FieldDefinition;
import java.util.List;

public interface GetFormStructureUseCase {
    List<FieldDefinition> execute();
}