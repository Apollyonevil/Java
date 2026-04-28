package com.civica.newhires.forms.infrastructure.adapters.output.persistence;

import com.civica.newhires.fields.infrastructure.adapters.output.persistence.entities.FieldDefinitionEntity;
import com.civica.newhires.fields.infrastructure.adapters.output.persistence.repository.JpaFieldDefinitionRepository;
import com.civica.newhires.forms.domain.model.FormVersion;
import com.civica.newhires.forms.domain.model.FormVersionField;
import com.civica.newhires.forms.domain.ports.output.FormVersionPort;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.entities.FormVersionFieldEntity;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.mappers.FormVersionPersistenceMapper;
import com.civica.newhires.forms.infrastructure.adapters.output.persistence.repository.JpaFormVersionRepository;
import lombok.RequiredArgsConstructor;
import com.civica.newhires.fields.domain.exception.FieldNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormVersionPersistenceAdapter implements FormVersionPort {

    private final JpaFormVersionRepository versionRepo;
    private final JpaFieldDefinitionRepository fieldRepo;
    private final FormVersionPersistenceMapper mapper;

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

        FormVersionEntity saved = versionRepo.save(entity);

        if (version.getFields() != null) {
            List<FormVersionFieldEntity> fieldEntities = version.getFields().stream()
                    .map(vf -> {
                        FieldDefinitionEntity fieldEntity = fieldRepo.findById(vf.getField().getId())
                                .orElseThrow(() -> new FieldNotFoundException(vf.getField().getId()));

                        FormVersionFieldEntity vfe = new FormVersionFieldEntity();
                        vfe.setId(vf.getId()); 
                        vfe.setVersion(saved);
                        vfe.setSortOrder(vf.getSortOrder());
                        vfe.setField(fieldEntity);
                        return vfe;
                    })
                    .collect(Collectors.toList());

            saved.setFields(fieldEntities);
            versionRepo.save(saved);
        }

        return mapper.toDomain(saved); // ← delega al mapper
    }

    @Override
    public Optional<FormVersion> findById(UUID id) {
        return versionRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<FormVersion> findActive() {
        return versionRepo.findByActiveTrue().map(mapper::toDomain);
    }

    @Override
    public List<FormVersion> findAll() {
        return versionRepo.findAllByOrderByVersionNumberDesc()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deactivateAll() {
        versionRepo.deactivateAll(); 
    }

    @Override
    public void deleteById(UUID id) {
        versionRepo.deleteById(id);
    }
}