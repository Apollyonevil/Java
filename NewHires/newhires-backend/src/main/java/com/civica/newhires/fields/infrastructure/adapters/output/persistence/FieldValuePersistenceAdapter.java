package com.civica.newhires.fields.infrastructure.adapters.output.persistence;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionFieldEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFormVersionFieldRepository;
import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import com.civica.newhires.fields.domain.ports.output.FormPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FieldValuePersistenceAdapter implements FormPort {

    private final JpaFieldDefinitionRepository definitionRepo;
    private final JpaFieldValueRepository valueRepo; 
    private final JpaFormVersionFieldRepository versionFieldRepo; 
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
    @Transactional
    public void saveValues(List<FieldValue> values) {
        if (values == null || values.isEmpty()) {
            log.warn("[DB-SAVE] La lista de valores está vacía, nada que guardar.");
            return;
        }

        log.info("[DB-SAVE] Iniciando mapeo de {} valores...", values.size());
        
        var entities = values.stream()
                .map(mapper::toEntity)
                .toList();

 
        entities.forEach(e -> log.info("[DB-SAVE] Preparando insert: Campo={}, Val={}, FileID={}", 
                e.getFieldDefinitionId(), e.getValue(), e.getFileResourceId()));

  
        valueRepo.saveAll(entities);
        
      
        valueRepo.flush(); 
        
        log.info("[DB-SAVE] ¡saveAll y flush completados con éxito!");
    }

    @Override
    @Transactional
    public FieldDefinition saveDefinition(FieldDefinition definition) {
   
        definitionRepo.findById(definition.getId()).ifPresent(existing -> {
            existing.getOptions().clear();
            definitionRepo.saveAndFlush(existing);
        });
        
        var entity = mapper.toEntity(definition);
        var savedEntity = definitionRepo.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void deleteDefinition(UUID id) {
    
        FieldDefinitionEntity field = definitionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Campo no encontrado"));


        if (field.isActive()) {
            throw new RuntimeException("No se puede eliminar un campo activo. Desactívalo primero.");
        }


        List<FormVersionFieldEntity> versionsUsingField = versionFieldRepo.findByFieldId(id);
        
        if (!versionsUsingField.isEmpty()) {
    
            String versionNumbers = versionsUsingField.stream()
                    .map(vf -> vf.getVersion().getVersionNumber().toString())
                    .distinct()
                    .collect(Collectors.joining(", "));
            
            throw new RuntimeException("Este campo no se puede borrar porque forma parte de la versión de formulario " + versionNumbers);
        }

        definitionRepo.deleteById(id);
    }
}