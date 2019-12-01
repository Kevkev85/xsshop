package com.example.jwtsecurity.Views;

import javax.validation.constraints.NotNull;

public class ProductToItemView {
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private double price;

    @NotNull
    private CategoryView category;

    @NotNull
    private  String imageUrl;

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

    public CategoryView getCategory() {
        return category;
    }

    public void setCategory(CategoryView category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
