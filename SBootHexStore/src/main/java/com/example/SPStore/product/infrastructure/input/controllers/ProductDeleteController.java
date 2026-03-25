package com.example.SPStore.product.infrastructure.input.controllers;

import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.domain.ports.in.ProductFindByIdInputPort;
import com.example.SPStore.product.domain.ports.in.ProductDeleteInputPort;
import com.example.SPStore.shared.infrastructure.input.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductDeleteController {

    @Autowired
    private ProductFindByIdInputPort productFindByIdInputPort;

    @Autowired
    private ProductDeleteInputPort productDeleteInputPort;

    @Autowired
    private UploadFileService upload;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ProductDTO pDTO = productFindByIdInputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        if (pDTO.getImage() != null && !pDTO.getImage().equals("default.jpg")) {
            upload.deleteImage(pDTO.getImage());
        }
        productDeleteInputPort.delete(id);
        return ResponseEntity.noContent().build(); 
    }
}