package com.example.SPStore.product.application.service;

import com.example.SPStore.product.domain.ports.in.ProductDeleteInputPort;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;

public class ProductDeleteService implements ProductDeleteInputPort {

    private final IProductPersistencePort productPersistencePort;

    public ProductDeleteService(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void delete(Integer id) {
        productPersistencePort.delete(id);
    }
}