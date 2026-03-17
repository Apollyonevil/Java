package com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaFieldValueRepository extends JpaRepository<FieldValueEntity, UUID> {
    // Útil para cuando RRHH quiera ver todas las respuestas de un empleado concreto
    List<FieldValueEntity> findByEmployeeId(UUID employeeId);
}