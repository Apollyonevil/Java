package com.example.SPStore.product.application.dto;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private String image;
    private int amount;

    public ProductDTO() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}