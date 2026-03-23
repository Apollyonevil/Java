package com.civica.newhires.forms.infrastructure.adapters.output.persistence;

import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.model.FormVersionField;
import com.civica.newhires.forms.domain.ports.output.FormVersionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionFieldEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers.FormPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFormVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormVersionPersistenceAdapter implements FormVersionRepository {

    private final JpaFormVersionRepository versionRepo;
    private final JpaFieldDefinitionRepository fieldRepo;
    private final FormPersistenceMapper mapper;

    @Override
    @Transactional
    public FormVersion save(FormVersion version) {
        FormVersionEntity entity = new FormVersionEntity();
        entity.setId(version.getId());
        entity.setVersionNumber(version.getVersionNumber());
        entity.setCreatedAt(version.getCreatedAt());
        entity.setCreatedBy(version.getCreatedBy());
        entity.setDescription(version.getDescription());
        entity.setActive(version.isActive());

        // Guardamos primero la versión sin campos
        FormVersionEntity saved = versionRepo.save(entity);

        // Luego construimos y asignamos los campos
        if (version.getFields() != null) {
            List<FormVersionFieldEntity> fieldEntities = version.getFields().stream()
                    .map(vf -> {
                        FormVersionFieldEntity vfe = new FormVersionFieldEntity();
                        vfe.setId(vf.getId() != null ? vf.getId() : UUID.randomUUID());
                        vfe.setVersion(saved);
                        vfe.setSortOrder(vf.getSortOrder());

                        FieldDefinitionEntity fieldEntity = fieldRepo.findById(vf.getField().getId())
                                .orElseThrow(() -> new RuntimeException("Campo no encontrado: " + vf.getField().getId()));
                        vfe.setField(fieldEntity);

                        return vfe;
                    })
                    .collect(Collectors.toList());

            saved.setFields(fieldEntities);
            versionRepo.save(saved);
        }

        return toDomain(saved);
    }

    @Override
    public Optional<FormVersion> findById(UUID id) {
        return versionRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<FormVersion> findActive() {
        return versionRepo.findByActiveTrue().map(this::toDomain);
    }

    @Override
    public List<FormVersion> findAll() {
        return versionRepo.findAllByOrderByVersionNumberDesc()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deactivateAll() {
        versionRepo.findAll().forEach(v -> {
            v.setActive(false);
            versionRepo.save(v);
        });
    }

    @Override
    public void deleteById(UUID id) {
        versionRepo.deleteById(id);
    }

    private FormVersion toDomain(FormVersionEntity entity) {
        List<FormVersionField> fields = entity.getFields() == null ? List.of() :
                entity.getFields().stream()
                        .map(vf -> new FormVersionField(
                                vf.getId(),
                                entity.getId(),
                                mapper.toDomain(vf.getField()),
                                vf.getSortOrder()
                        ))
                        .collect(Collectors.toList());

        return new FormVersion(
                entity.getId(),
                entity.getVersionNumber(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getDescription(),
                entity.isActive(),
                fields
        );
    }
    
}