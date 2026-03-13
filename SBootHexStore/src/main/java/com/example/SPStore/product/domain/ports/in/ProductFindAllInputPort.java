package com.example.SPStore.product.domain.ports.in;

import com.example.SPStore.product.application.dto.ProductDTO;
import java.util.List;

public interface ProductFindAllInputPort {
    
    List<ProductDTO> findAll(); 

}