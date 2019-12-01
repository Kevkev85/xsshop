package com.example.jwtsecurity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
//    @JsonIgnore
//    private ShoppingCart shoppingCart;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

    private String imageUrl;

    private Integer quantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ShoppingCart shoppingCart;

    public Item(){}

    public Item(String title, double price, String imageUrl ) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;

    }

    public Item(String title, double price, Category category, String imageUrl) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void addQuantity() {
        this.setQuantity(this.quantity + 1);
    }

    public void reduceQuantity() {
        this.setQuantity(this.quantity - 1);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof  Item)) return false;
        return id != null && id.equals(((Item) o).getId());
    }
}
