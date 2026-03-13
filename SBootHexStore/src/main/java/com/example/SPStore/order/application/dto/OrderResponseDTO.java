package com.example.SPStore.order.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.example.SPStore.orderdetail.application.dto.OrderDetailDTO;


public class OrderResponseDTO {
    private Integer id;
    private String num;
    private LocalDateTime dateCreation;
    private double total;
    private String userName; 
    private List<OrderDetailDTO> details; 

    public OrderResponseDTO() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNum() { return num; }
    public void setNum(String num) { this.num = num; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

}