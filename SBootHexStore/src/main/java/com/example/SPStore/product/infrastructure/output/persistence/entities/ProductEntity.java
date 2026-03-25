package com.example.SPStore.product.infrastructure.output.persistence.entities;

import com.example.SPStore.user.infrastructure.output.persistence.entities.UserEntity; 
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String image;
    private String address;
    private double price;
    private int amount;
    
    @ManyToOne
    private UserEntity user; 

  
    public ProductEntity() {
    }

  
    public ProductEntity(Integer id, String name, String description, String image, String address, double price, int amount, UserEntity user) {
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

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
    
    @Override
    public String toString() {
        return "ProductEntity [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
                + ", address=" + address + ", price=" + price + ", amount=" + amount + "]";
    }
}