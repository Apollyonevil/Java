package com.example.SPStore.order.infrastructure.input.controllers;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class OrderDeleteController {

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteProductCart(@PathVariable Integer id, HttpSession session) {
        List<OrderDetails> details = (List<OrderDetails>) session.getAttribute("cart");
        Order order = (Order) session.getAttribute("order");

        if (details != null) {
            details.removeIf(dt -> dt.getProduct().getId().equals(id));
            order.setTotal(details.stream().mapToDouble(OrderDetails::getTotal).sum());
            session.setAttribute("cart", details);
            session.setAttribute("order", order);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}