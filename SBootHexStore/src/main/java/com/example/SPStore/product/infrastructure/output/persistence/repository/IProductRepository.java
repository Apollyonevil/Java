package com.example.SPStore.product.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {
  
}