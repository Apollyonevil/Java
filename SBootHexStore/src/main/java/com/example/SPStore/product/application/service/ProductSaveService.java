package com.example.SPStore.product.application.service;

import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.domain.ports.in.ProductSaveInputPort;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;


public class ProductSaveService implements ProductSaveInputPort {

    private final IProductPersistencePort productPersistencePort;

    public ProductSaveService(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public Product save(Product product) {

        return productPersistencePort.save(product);
    }

}