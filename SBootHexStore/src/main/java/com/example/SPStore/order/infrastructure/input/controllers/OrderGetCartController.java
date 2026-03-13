package com.example.SPStore.order.infrastructure.input.controllers;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class OrderGetCartController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        response.put("cart", session.getAttribute("cart"));
        response.put("order", session.getAttribute("order"));
        
        return ResponseEntity.ok(response); 
    }
}