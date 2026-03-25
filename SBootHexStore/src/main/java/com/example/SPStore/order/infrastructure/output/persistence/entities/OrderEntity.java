package com.example.SPStore.order.infrastructure.output.persistence.entities;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

import com.example.SPStore.order.infrastructure.output.persistence.entities.OrderEntity;
import com.example.SPStore.orderdetail.infrastructure.output.persistence.entities.OrderDetailsEntity;
import com.example.SPStore.user.infrastructure.output.persistence.entities.UserEntity;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String num;
    private LocalDateTime dateCreation; 
    private LocalDateTime dateReceipt;  
    private double total;

    @ManyToOne
    private UserEntity user; 

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> detail;

    public OrderEntity() {
    }


    public OrderEntity(Integer id, String num, LocalDateTime dateCreation, LocalDateTime dateReceipt, double total) {
        this.id = id;
        this.num = num;
        this.dateCreation = dateCreation;
        this.dateReceipt = dateReceipt;
        this.total = total;
    }

   
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

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public List<OrderDetailsEntity> getDetail() { return detail; }
    public void setDetail(List<OrderDetailsEntity> detail) { this.detail = detail; }

    @Override
    public String toString() {
        return "OrderEntity [id=" + id + ", num=" + num + ", dateCreation=" + dateCreation + 
                ", dateReceipt=" + dateReceipt + ", total=" + total + "]";
    }
}