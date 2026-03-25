package com.example.SPStore.user.domain.model;

import java.util.ArrayList;
import java.util.List;
import com.example.SPStore.product.domain.model.Product;
import com.example.SPStore.order.domain.model.Order;

public class User {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String phone;
    private String type;
    private String password;

    private List<Product> products;
    private List<Order> orders= new ArrayList<>();

    public User() {
    }

    public User(Integer id, String name, String username, String email, String address, String phone, String type, String password) {
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

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public List<Order> getOrders() {if (orders == null) orders = new ArrayList<>();return orders;}
    public void setOrders(List<Order> orders) { this.orders = orders; }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", address="
                + address + ", phone=" + phone + ", type=" + type + ", password=" + password + "]";
    }
}