package com.civica.newhires.forms.application.service;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.ports.input.ManageFieldsUseCase;
import com.civica.newhires.forms.domain.ports.output.FormRepository;

import jakarta.transaction.Transactional;
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
@Transactional // Asegúrate de tener esta anotación para que la sesión esté abierta
public FieldDefinition updateField(UUID id, FieldDefinition field) {
    // 1. Validar que existe (opcional pero recomendado)
    formRepository.findDefinitionById(id); 

    // 2. Crear instancia nueva
    FieldDefinition updatedField = new FieldDefinition(
        id, 
        field.getLabel(), 
        field.getType(), 
        field.isRequired(), 
        field.getPlaceholder(), 
        field.getOptions(),
        field.getSortOrder()
    );

    // 3. Persistir y forzar el refresco
    FieldDefinition saved = formRepository.saveDefinition(updatedField);
    
    // IMPORTANTE: Forzamos a Hibernate a cargar las opciones para el JSON
    if (saved.getOptions() != null) {
        saved.getOptions().size(); // Esto "despierta" a la colección si es Lazy
    }
    
    return saved;
}

    @Override
    public void deleteField(UUID id) {
        formRepository.deleteDefinition(id);
    }
}