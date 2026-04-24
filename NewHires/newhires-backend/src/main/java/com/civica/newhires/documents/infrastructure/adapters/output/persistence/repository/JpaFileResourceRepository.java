package com.civica.newhires.documents.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.documents.infrastructure.adapters.output.persistence.entities.FileResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface JpaFileResourceRepository extends JpaRepository<FileResourceEntity, UUID> {
}