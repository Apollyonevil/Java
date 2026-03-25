package com.example.SPStore.user.infrastructure.output.persistence.entities;

import com.example.SPStore.order.infrastructure.output.persistence.entities.OrderEntity;
import com.example.SPStore.product.infrastructure.output.persistence.entities.ProductEntity;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String phone;
    private String type;
    private String password;


    @OneToMany(mappedBy = "user")
    private List<ProductEntity> products; 

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders; 

    public UserEntity() { 
    }

    public UserEntity(Integer id, String name, String username, String email, String address, String phone, String type, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.password = password;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

  
    public List<ProductEntity> getProducts() { return products; }
    public void setProducts(List<ProductEntity> products) { this.products = products; }

    public List<OrderEntity> getOrders() { return orders; }
    public void setOrders(List<OrderEntity> orders) { this.orders = orders; }

    @Override
    public String toString() {
        return "UserEntity [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + "]";
    }
}