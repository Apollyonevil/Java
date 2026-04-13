package com.civica.newhires.fields.infrastructure.adapters.output.persistence;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldValueRepository;
import com.civica.newhires.fields.domain.model.FieldDefinition;
import com.civica.newhires.fields.domain.model.FieldValue;
import com.civica.newhires.fields.domain.ports.output.FormPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FieldValuePersistenceAdapter implements FormPort {

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

        // Log de depuración para verificar qué llega a la base de datos
        entities.forEach(e -> log.info("[DB-SAVE] Preparando insert: Campo={}, Val={}, FileID={}", 
                e.getFieldDefinitionId(), e.getValue(), e.getFileResourceId()));

        // Guardamos todo el lote
        valueRepo.saveAll(entities);
        
        // Sincronización inmediata con MariaDB
        valueRepo.flush(); 
        
        log.info("[DB-SAVE] ¡saveAll y flush completados con éxito!");
    }

    @Override
    @Transactional
    public FieldDefinition saveDefinition(FieldDefinition definition) {
        // Limpieza de opciones previas si ya existe (para evitar duplicados en actualizaciones)
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
        definitionRepo.deleteById(id);
    }
}