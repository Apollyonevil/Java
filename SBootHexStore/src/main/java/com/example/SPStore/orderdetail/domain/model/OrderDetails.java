package com.example.SPStore.orderdetail.domain.model;

import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.order.domain.model.Order; 

public class OrderDetails {
    private Integer id;
    private String name;
    private double amount;
    private double price;
    private double total;

    private Order order; 
    private Product product; 

    public OrderDetails() {
    }

    public OrderDetails(Integer id, String name, double amount, double price, double total) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.total = total;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", name=" + name + ", amount=" + amount + 
               ", price=" + price + ", total=" + total + "]";
    }
}