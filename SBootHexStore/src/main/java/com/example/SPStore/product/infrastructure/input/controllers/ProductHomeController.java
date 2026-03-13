package com.example.SPStore.product.infrastructure.input.controllers;

import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.domain.ports.in.ProductFindByIdInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductHomeController {

    @Autowired
    private ProductFindByIdInputPort productFindByIdInputPort;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> productHome(@PathVariable Integer id) {
        return productFindByIdInputPort.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}