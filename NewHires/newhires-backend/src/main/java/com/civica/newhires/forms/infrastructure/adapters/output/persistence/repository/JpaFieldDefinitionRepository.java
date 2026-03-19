package com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaFieldDefinitionRepository extends JpaRepository<FieldDefinitionEntity, UUID> {
    
    List<FieldDefinitionEntity> findAllByOrderBySortOrderAsc();
}