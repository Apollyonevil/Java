package com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldValueEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaFieldValueRepository extends JpaRepository<FieldValueEntity, UUID> {
    // Útil para cuando RRHH quiera ver todas las respuestas de un empleado concreto
    List<FieldValueEntity> findByEmployeeId(UUID employeeId);
}