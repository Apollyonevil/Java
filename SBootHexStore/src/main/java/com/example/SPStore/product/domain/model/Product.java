package com.example.SPStore.product.domain.model;

import com.example.SPStore.user.domain.model.User;

public class Product {

    private Integer id;
    private String name;
    private String description;
    private String image;
    private String address;
    private double price;
    private int amount;
    
    private User user;

    public Product() {
    }

    public Product(Integer id, String name, String description, String image, String address, double price, int amount, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.address = address;
        this.price = price;
        this.amount = amount;
        this.user = user;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
                + ", address=" + address + ", price=" + price + ", amount=" + amount + "]";
    }
}