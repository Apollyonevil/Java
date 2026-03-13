package com.example.SPStore.product.infrastructure.input.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductCreateController {


    @GetMapping("/create")
    public ResponseEntity<Void> create() {
  
        return ResponseEntity.ok().build();
    }
}