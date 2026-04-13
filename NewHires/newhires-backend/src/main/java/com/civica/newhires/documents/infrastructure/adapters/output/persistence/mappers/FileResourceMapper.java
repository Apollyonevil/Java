package com.civica.newhires.documents.infrastructure.adapters.output.persistence.mappers;

import com.civica.newhires.documents.domain.model.FileResource;
import com.civica.newhires.documents.infrastructure.adapters.output.persistence.entities.FileResourceEntity;
import org.springframework.stereotype.Component;

@Component
public class FileResourceMapper {

    public FileResourceEntity toEntity(FileResource domain) {
        if (domain == null) return null;
        
        FileResourceEntity entity = new FileResourceEntity();
        entity.setId(domain.getId());
        entity.setPath(domain.getPath());
        entity.setOriginalName(domain.getOriginalName());
        entity.setMimeType(domain.getMimeType());
        entity.setSize(domain.getSize());
        
        return entity;
    }

    public FileResource toDomain(FileResourceEntity entity) {
        if (entity == null) return null;
        
    
        return new FileResource(
            entity.getId(),
            entity.getOriginalName(),
            entity.getMimeType(),
            entity.getSize(),
            entity.getPath()
        );
    }
}