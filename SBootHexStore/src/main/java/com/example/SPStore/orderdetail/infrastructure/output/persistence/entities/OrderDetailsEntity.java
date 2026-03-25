package com.example.SPStore.orderdetail.infrastructure.output.persistence.entities;

import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity;
import com.example.SPStore.order.infrastructure.output.persistence.entities.OrderEntity;


import jakarta.persistence.*;

@Entity
@Table(name = "details")
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double amount;
    private double price;
    private double total;
   
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    private ProductEntity product;


    public OrderDetailsEntity(){
    }
    

    public OrderDetailsEntity(Integer id, String name, double amount, double price, double total) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.total = total;
    }

  
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}
    
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
   
    public double getTotal() {return total;}
    public void setTotal(double total) {this.total = total;}

 
    public OrderEntity getOrder() {return order;}
    public void setOrder(OrderEntity order) {this.order = order;}
    
    public ProductEntity getProduct() {return product;}
    public void setProduct(ProductEntity product) {this.product = product;}

    @Override
    public String toString() {
        return "OrderDetailsEntity [id=" + id + ", name=" + name + ", amount=" + amount + ", price=" + price + ", total=" + total +"]";
    }
}