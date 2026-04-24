package com.civica.newhires.documents.infrastructure.adapters.output.persistence;

import com.civica.newhires.documents.domain.model.FileResource;
import com.civica.newhires.documents.domain.ports.output.FileResourceRepository;
import com.civica.newhires.documents.infrastructure.adapters.output.persistence.entities.FileResourceEntity;
import com.civica.newhires.documents.infrastructure.adapters.output.persistence.mappers.FileResourceMapper;
import com.civica.newhires.documents.infrastructure.adapters.output.persistence.repository.JpaFileResourceRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FileResourcePersistenceAdapter implements FileResourceRepository {

    private final JpaFileResourceRepository jpaRepository;
    private final FileResourceMapper mapper;

    public FileResourcePersistenceAdapter(JpaFileResourceRepository jpaRepository, FileResourceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public FileResource save(FileResource fileResource) {
        FileResourceEntity entity = mapper.toEntity(fileResource);
        // Usamos saveAndFlush para que Hibernate no espere al final del método
        FileResourceEntity savedEntity = jpaRepository.saveAndFlush(entity); 
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<FileResource> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }


    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}