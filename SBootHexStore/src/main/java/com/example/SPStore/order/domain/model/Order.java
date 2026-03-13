package com.example.SPStore.order.domain.model; // Ajusta el package a su nueva ruta

import java.time.LocalDateTime; // Mejor que java.util.Date
import java.util.ArrayList;
import java.util.List;
import com.example.SPStore.user.domain.model.User; 
import com.example.SPStore.orderdetail.domain.model.OrderDetails; 

public class Order {
    private Integer id;
    private String num;
    private LocalDateTime dateCreation; 
    private LocalDateTime dateReceipt;
    private double total;

    private User user; // Ahora Java ya sabe qué es "User"
    private List<OrderDetails> detail = new ArrayList<>();
    public Order() {
    }

    public Order(Integer id, String num, LocalDateTime dateCreation, LocalDateTime dateReceipt, double total) {
        this.id = id;
        this.num = num;
        this.dateCreation = dateCreation;
        this.dateReceipt = dateReceipt;
        this.total = total;
    }

 

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateReceipt() { return dateReceipt; }
    public void setDateReceipt(LocalDateTime dateReceipt) { this.dateReceipt = dateReceipt; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<OrderDetails> getDetails() {
        if (detail == null) {
            detail = new ArrayList<>();
        }
        return detail;
    }

    public void setDetails(List<OrderDetails> details) {
        this.detail = details;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", num=" + num + ", dateCreation=" + dateCreation + 
               ", dateReceipt=" + dateReceipt + ", total=" + total + "]";
    }
}