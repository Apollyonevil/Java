package com.example.SPStore.product.application.service;

import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.application.mapper.DTOConverter;
import com.example.SPStore.product.domain.ports.in.ProductFindByIdInputPort;
import com.example.SPStore.product.domain.ports.out.IProductPersistencePort;

import java.util.Optional;


public class ProductFindByIdService implements ProductFindByIdInputPort {

    private final IProductPersistencePort productPersistencePort;

    public ProductFindByIdService(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }


    @Override
    public Optional<ProductDTO> findById(Integer id) {
  
        return productPersistencePort.findById(id)
                .map(DTOConverter::toProductDTO);
    }

}