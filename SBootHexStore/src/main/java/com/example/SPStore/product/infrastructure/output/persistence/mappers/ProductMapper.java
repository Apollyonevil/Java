package com.example.SPStore.product.infrastructure.output.persistence.mappers;

import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(Product product) {
        if (product == null) return null;
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setImage(product.getImage());
        entity.setAddress(product.getAddress());
        entity.setPrice(product.getPrice());
        entity.setAmount(product.getAmount());
        return entity;
    }

    public Product toDomain(ProductEntity entity) {
        if (entity == null) return null;
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setImage(entity.getImage());
        product.setAddress(entity.getAddress());
        product.setPrice(entity.getPrice());
        product.setAmount(entity.getAmount());
        return product;
    }
}