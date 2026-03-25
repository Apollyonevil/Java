package com.example.SPStore.product.infrastructure.input.controllers;

import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.domain.ports.in.ProductFindByIdInputPort;
import com.example.SPStore.product.domain.ports.in.ProductUpdateInputPort;
import com.example.SPStore.shared.infrastructure.input.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductUpdateController {

    @Autowired
    private ProductFindByIdInputPort productFindByIdInputPort;

    @Autowired
    private ProductUpdateInputPort productUpdateInputPort;

    @Autowired
    private UploadFileService upload;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, Product product, @RequestParam(value = "img", required = false) MultipartFile file) throws IOException {
        ProductDTO pDTO = productFindByIdInputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setId(id);

        if (file == null || file.isEmpty()) {
            product.setImage(pDTO.getImage());
        } else {
            if (pDTO.getImage() != null && !pDTO.getImage().equals("default.jpg")) {
                upload.deleteImage(pDTO.getImage());
            }
            String nombreImagen = upload.saveImage(file);
            product.setImage(nombreImagen);
        }

        productUpdateInputPort.update(product);
        return ResponseEntity.ok().build();
    }
}