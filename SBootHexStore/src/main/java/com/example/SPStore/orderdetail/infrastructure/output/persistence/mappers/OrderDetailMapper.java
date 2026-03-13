package com.example.SPStore.orderdetail.infrastructure.output.persistence.mappers;

import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import com.example.SPStore.orderdetail.infrastructure.output.persistence.entities.OrderDetailsEntity;
import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity; // Importa esto
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    public OrderDetailsEntity toEntity(OrderDetails domain) {
        if (domain == null) return null;
        OrderDetailsEntity entity = new OrderDetailsEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setAmount(domain.getAmount());
        entity.setPrice(domain.getPrice());
        entity.setTotal(domain.getTotal());
        

        if (domain.getProduct() != null) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(domain.getProduct().getId());
            entity.setProduct(productEntity);
        }
      
        return entity;
    }

    public OrderDetails toDomain(OrderDetailsEntity entity) {
        if (entity == null) return null;
        OrderDetails domain = new OrderDetails();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setAmount(entity.getAmount());
        domain.setPrice(entity.getPrice());
        domain.setTotal(entity.getTotal());
        
        // AÑADE ESTO: Para que el dominio tenga la referencia del producto
        if (entity.getProduct() != null) {
            com.example.SPStore.product.domain.model.Product product = new com.example.SPStore.product.domain.model.Product();
            product.setId(entity.getProduct().getId());
            domain.setProduct(product);
        }
        
        return domain;
    }
}