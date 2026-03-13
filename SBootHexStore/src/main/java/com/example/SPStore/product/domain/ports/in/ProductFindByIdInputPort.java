package com.example.SPStore.product.domain.ports.in;

import com.example.SPStore.product.application.dto.ProductDTO;
import java.util.Optional;

public interface ProductFindByIdInputPort {

    Optional<ProductDTO> findById(Integer id); 
    
}