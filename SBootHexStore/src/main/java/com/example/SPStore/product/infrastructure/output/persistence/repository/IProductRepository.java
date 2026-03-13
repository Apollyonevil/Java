package com.example.SPStore.product.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Importamos la ENTIDAD que tiene las anotaciones @Entity
import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {
    // Ahora Spring ya sabe que debe mapear los resultados a ProductEntity
}