package com.example.SPStore.product.application.service;

import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.domain.ports.in.ProductUpdateInputPort;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;


public class ProductUpdateService implements ProductUpdateInputPort {

    private final IProductPersistencePort productPersistencePort;

    public ProductUpdateService(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }


    @Override
    public void update(Product product) {
        productPersistencePort.update(product);
    }

}