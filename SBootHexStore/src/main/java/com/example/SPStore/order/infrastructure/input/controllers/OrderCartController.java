package com.example.SPStore.order.infrastructure.input.controllers;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import com.example.SPStore.product.application.dto.ProductDTO;
import com.example.SPStore.product.application.mapper.DTOConverter;
import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.domain.ports.in.ProductFindByIdInputPort;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class OrderCartController {

    @Autowired
    private ProductFindByIdInputPort productFindByIdInputPort;

    @PostMapping("/add")
    public ResponseEntity<Void> addCart(@RequestParam Integer id, @RequestParam Integer amount, HttpSession session) {
        List<OrderDetails> details = (List<OrderDetails>) session.getAttribute("cart");
        if (details == null) details = new ArrayList<>();

        Order order = (Order) session.getAttribute("order");
        if (order == null) order = new Order();

        ProductDTO productDTO = productFindByIdInputPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        Product product = DTOConverter.toProduct(productDTO);

        if (amount > product.getAmount()) {
            return ResponseEntity.badRequest().build(); 
        }

        boolean isPresent = details.stream().anyMatch(p -> p.getProduct().getId().equals(id));

        if (!isPresent) {
            OrderDetails od = new OrderDetails();
            od.setAmount(amount);
            od.setPrice(product.getPrice());
            od.setName(product.getName());
            od.setTotal(product.getPrice() * amount);
            od.setProduct(product);
            details.add(od);
        } else {
            for (OrderDetails od : details) {
                if (od.getProduct().getId().equals(id)) {
                    double nuevaCant = od.getAmount() + amount;
                    od.setAmount(nuevaCant);
                    od.setTotal(od.getPrice() * nuevaCant);
                }
            }
        }

        order.setTotal(details.stream().mapToDouble(OrderDetails::getTotal).sum());
        session.setAttribute("cart", details);
        session.setAttribute("order", order);

        return ResponseEntity.ok().build(); 
    }
}