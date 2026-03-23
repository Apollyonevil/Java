package com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface JpaFormVersionFieldRepository extends JpaRepository<FormVersionFieldEntity, UUID> {
}