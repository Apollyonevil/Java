package com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository;

import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaFormVersionRepository extends JpaRepository<FormVersionEntity, UUID> {
    Optional<FormVersionEntity> findByActiveTrue();
    List<FormVersionEntity> findAllByOrderByVersionNumberDesc();

        @Modifying
    @Query("UPDATE FormVersionEntity v SET v.active = false")
    void deactivateAll();
}