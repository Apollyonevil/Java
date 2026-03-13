package com.example.SPStore.order.infrastructure.input.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderSuccessController {

    @GetMapping("/success-status")
    public ResponseEntity<Map<String, String>> success(HttpSession session) {
        Object idUser = session.getAttribute("iduser");
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "completed");
        response.put("userId", idUser != null ? idUser.toString() : "anonymous");
        response.put("message", "Order processed successfully");

        return ResponseEntity.ok(response);
    }
}