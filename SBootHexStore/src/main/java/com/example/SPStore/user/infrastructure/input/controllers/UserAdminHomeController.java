package com.example.SPStore.user.infrastructure.input.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.SPStore.product.domain.ports.in.ProductFindAllInputPort;
import com.example.SPStore.product.application.dto.ProductDTO;

@RestController
@RequestMapping("/api/admin")
public class UserAdminHomeController {

    @Autowired
    private ProductFindAllInputPort productFindAllInputPort;

    @GetMapping("/home")
    public ResponseEntity<List<ProductDTO>> adminHome() {
        return ResponseEntity.ok(productFindAllInputPort.findAll());
    }
}