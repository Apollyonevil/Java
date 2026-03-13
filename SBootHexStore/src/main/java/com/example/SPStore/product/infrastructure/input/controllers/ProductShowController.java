package com.example.SPStore.product.infrastructure.input.controllers;

import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.domain.ports.in.ProductFindAllInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductShowController {

    @Autowired
    private ProductFindAllInputPort productFindAllInputPort;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> show() {   
        return ResponseEntity.ok(productFindAllInputPort.findAll());
    }
}