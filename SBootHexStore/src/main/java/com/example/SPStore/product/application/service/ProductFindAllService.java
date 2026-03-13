package com.example.SPStore.product.application.service;

import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.application.mapper.DTOConverter;
import com.example.SPStore.product.domain.ports.in.ProductFindAllInputPort;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;

import java.util.List;
import java.util.stream.Collectors;

public class ProductFindAllService implements ProductFindAllInputPort {

    private final IProductPersistencePort productPersistencePort;

    public ProductFindAllService(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }


    @Override
    public List<ProductDTO> findAll() {
        return productPersistencePort.findAll().stream()
                .map(DTOConverter::toProductDTO)
                .collect(Collectors.toList());
    }

}