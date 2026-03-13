package com.example.SPStore.product.domain.ports.in;

import com.example.SPStore.product.domain.model.Product;

public interface ProductUpdateInputPort {

    void update(Product product);

}