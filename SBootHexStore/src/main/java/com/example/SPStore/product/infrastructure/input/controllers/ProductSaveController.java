package com.example.SPStore.product.infrastructure.input.controllers;

import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.product.domain.ports.in.ProductSaveInputPort;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import com.example.SPStore.shared.infrastructure.input.services.UploadFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductSaveController {

    @Autowired
    private ProductSaveInputPort productSaveInputPort;

    @Autowired
    private UserFindByIdInputPort userFindByIdInputPort;

    @Autowired
    private UploadFileService upload;

    @PostMapping
    public ResponseEntity<Product> save(Product product, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        Object idUser = session.getAttribute("iduser");
        if (idUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User u = userFindByIdInputPort.findById(Integer.parseInt(idUser.toString()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        product.setUser(u);

        if (!file.isEmpty()) {
            String nombreImagen = upload.saveImage(file);
            product.setImage(nombreImagen);
        } else {
            product.setImage("default.jpg");
        }
        
        productSaveInputPort.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED); 
    }
}