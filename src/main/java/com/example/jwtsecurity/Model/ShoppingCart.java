package com.example.jwtsecurity.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdOn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shoppingCart", cascade =  CascadeType.ALL)
    private List<Item> items = new ArrayList<>();



    public ShoppingCart() {
    }

    public ShoppingCart(Date createdOn) {
        this.createdOn = createdOn;
    }

    public ShoppingCart(Date createdOn, List<Item> items) {
        this.createdOn = createdOn;
        this.items = items;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn() {
        this.createdOn = new Date();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public void addItem(Item item) {
        items.add(item);
        item.setShoppingCart(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setShoppingCart(null);

    }
}
