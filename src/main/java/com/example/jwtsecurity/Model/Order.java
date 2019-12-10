package com.example.jwtsecurity.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "finalOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date datePlaced;

    private String name;

    private String addressLine1;

    private String addressLine2;

    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private double totalPrice;

    public Order(){ }

//    public Order(Date datePlaced, String addressLine1, String addressLine2, String city, double totalPrice) {
//        this.datePlaced = datePlaced;
//        this.addressLine1 = addressLine1;
//        this.addressLine2 = addressLine2;
//        this.city = city;
//        this.totalPrice = totalPrice;
//    }


    public Order(String name, String addressLine1, String addressLine2, String city, List<OrderItem> orderItems, double totalPrice) {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public Order(String name, String addressLine1, String addressLine2, String city, double totalPrice) {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.totalPrice = totalPrice;
    }

    public Order(String name, String addressLine1, String addressLine2, String city, List<OrderItem> orderItems) {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced() {
        this.datePlaced = new Date();
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
