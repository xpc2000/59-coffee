package com.code59.caffemall.controller.Order.ClientAndShop;


public class OrderTemp {
    private String orderID;
    private String date;
    private String name;
    private String address;
    private String phone;
    private double totalPrice;
    private String beDeliver;
    private String beOver;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getBeDeliver() {
        return beDeliver;
    }

    public void setBeDeliver(String beDeliver) {
        this.beDeliver = beDeliver;
    }

    public String getBeOver() {
        return beOver;
    }

    public void setBeOver(String beOver) {
        this.beOver = beOver;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public OrderTemp(String orderID, String date, String name, String address, String phone, double totalPrice, String beDeliver, String beOver) {
        this.orderID = orderID;
        this.date = date;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.beDeliver = beDeliver;
        this.beOver = beOver;
    }

    @Override
    public String toString() {
        return "OrderTemp{" +
                "orderID='" + orderID + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", totalPrice=" + totalPrice +
                ", beDeliver='" + beDeliver + '\'' +
                ", beOver='" + beOver + '\'' +
                '}';
    }
}

