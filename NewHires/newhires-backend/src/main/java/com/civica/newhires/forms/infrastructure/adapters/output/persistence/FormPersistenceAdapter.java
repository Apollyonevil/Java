package com.civica.newhires.forms.infrastructure.adapters.output.persistence;

import com.civica.newhires.forms.domain.model.FieldDefinition;
import com.civica.newhires.forms.domain.model.FieldValue; 
import com.civica.newhires.forms.domain.ports.output.FormRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository; // Importante
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FormPersistenceAdapter implements FormRepository {

    private final JpaFieldDefinitionRepository definitionRepo;
    private final JpaFieldValueRepository valueRepo; 
    private final FormPersistenceMapper mapper;

    @Override
    public List<FieldDefinition> findAllFieldDefinitions() {
        return definitionRepo.findAllByOrderBySortOrderAsc().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public FieldDefinition findDefinitionById(UUID id) {
        return definitionRepo.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Campo no encontrado"));
    }

    @Override
    public FieldDefinition saveDefinition(FieldDefinition definition) {
        var entity = mapper.toEntity(definition);
        var savedEntity = definitionRepo.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteDefinition(UUID id) {
        definitionRepo.deleteById(id);
    }

    @Override
    public void saveValues(List<FieldValue> values) {
        var entities = values.stream()
                .map(mapper::toEntity)
                .toList();
        valueRepo.saveAll(entities);
    }
}