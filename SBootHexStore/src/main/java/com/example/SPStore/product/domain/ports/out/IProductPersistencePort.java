package com.example.SPStore.product.domain.ports.out;

import com.example.SPStore.product.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface IProductPersistencePort {
    Product save(Product product);
    Optional<Product> findById(Integer id);
    List<Product> findAll();
    void update(Product product);
    void delete(Integer id);      
}