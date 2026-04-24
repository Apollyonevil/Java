package com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaFieldDefinitionRepository extends JpaRepository<FieldDefinitionEntity, UUID> {
    
    List<FieldDefinitionEntity> findAllByOrderBySortOrderAsc();
}