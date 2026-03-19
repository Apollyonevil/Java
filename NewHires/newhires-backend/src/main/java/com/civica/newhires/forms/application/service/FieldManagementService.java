package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldManagementService implements ManageFieldsUseCase {

    private final FormRepository formRepository;

    @Override
    public FieldDefinition createField(FieldDefinition field) {
        return formRepository.saveDefinition(field);
    }

        @Override
        public FieldDefinition updateField(UUID id, FieldDefinition field) {
            // 1. Opcional: Validar que existe
            formRepository.findDefinitionById(id); 

            // 2. Creamos una instancia nueva con los datos actualizados
            // Usamos el constructor que acabamos de modificar
            FieldDefinition updatedField = new FieldDefinition(
                id,                             // El ID original de la URL
                field.getLabel(), 
                field.getType(), 
                field.isRequired(), 
                field.getPlaceholder(), 
                field.getOptions(),
                field.getSortOrder()            // <--- AQUÍ se guarda el nuevo orden
            );

            // 3. Persistimos
            return formRepository.saveDefinition(updatedField);
        }

    @Override
    public void deleteField(UUID id) {
        formRepository.deleteDefinition(id);
    }
}