package com.example.SPStore.product.domain.ports.in;

import com.example.SPStore.product.domain.model.Product;


public interface ProductSaveInputPort {
    Product save(Product product);
   
}