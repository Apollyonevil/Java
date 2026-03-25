package com.example.SPStore.product.infrastructure.output.persistence;

import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;
import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity;
import com.example.SPStore.product.infrastructure.output.persistence.repository.IProductRepository;
import com.example.SPStore.product.infrastructure.output.persistence.mappers.ProductMapper; 
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductPersistenceAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper; 

    public ProductPersistenceAdapter(IProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        return productMapper.toDomain(productRepository.save(entity));
    }

    @Override
    public Optional<Product> findById(Integer id) {

        return productRepository.findById(id).map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDomain) 
                .collect(Collectors.toList());
    }

    @Override
    public void update(Product product) {
   
        ProductEntity entity = productMapper.toEntity(product);
        productRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
    productRepository.deleteById(id);
}

 
}