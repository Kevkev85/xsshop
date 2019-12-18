package com.example.jwtsecurity.Views;

import java.util.Date;
import java.util.List;

public class OrderView {
    private Long id;

    private Date datePlaced;

    private  String name;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private List<OrderItemView> orderItems;

    private Long totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(Date datePlaced) {
        this.datePlaced = datePlaced;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public List<OrderItemView> getOrderItemViewList() {
//        return orderItemViewList;
//    }
//
//    public void setOrderItemViewList(List<OrderItemView> orderItemViewList) {
//        this.orderItemViewList = orderItemViewList;
//    }

    public List<OrderItemView> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemView> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
