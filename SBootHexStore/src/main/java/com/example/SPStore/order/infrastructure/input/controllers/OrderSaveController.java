package com.example.SPStore.order.infrastructure.input.controllers;

import com.example.SPStore.order.domain.model.Order;
import com.example.SPStore.order.domain.ports.in.OrderRegistrationInputPort;
import com.example.SPStore.orderdetail.domain.model.OrderDetails;
import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.product.domain.ports.in.ProductUpdateInputPort;
import com.example.SPStore.user.domain.model.User;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderSaveController {

    @Autowired
    private OrderRegistrationInputPort orderRegistrationInputPort;
    @Autowired
    private UserFindByIdInputPort userFindByIdInputPort;
    @Autowired
    private ProductUpdateInputPort productUpdateInputPort;

    @PostMapping("/save")
    public ResponseEntity<Void> saveOrder(HttpSession session) {
        Object idUser = session.getAttribute("iduser");
        List<OrderDetails> details = (List<OrderDetails>) session.getAttribute("cart");
        Order order = (Order) session.getAttribute("order");

        if (idUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (details == null || details.isEmpty()) return ResponseEntity.badRequest().build();

        User user = userFindByIdInputPort.findById(Integer.parseInt(idUser.toString())).get();
        order.setUser(user);
        order.setDetails(new ArrayList<>(details));

        for (OrderDetails dt : order.getDetails()) dt.setOrder(order);

        orderRegistrationInputPort.save(order);

        for (OrderDetails dt : details) {
            Product p = dt.getProduct();
            p.setAmount(p.getAmount() - (int)dt.getAmount());
            productUpdateInputPort.update(p);
        }

        session.removeAttribute("cart");
        session.removeAttribute("order");

        return new ResponseEntity<>(HttpStatus.CREATED); 
    }
}