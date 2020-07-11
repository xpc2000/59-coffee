package com.code59.caffemall.bean;

public class Order_shop {
    private String id;
    private String id_guest;
    private String orderType;
    private String time;
    private String deliverAddress;
    private String id_shop;
    private String phone;
    private String beOver;
    private String beDeliver;
    private double total_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_guest() {
        return id_guest;
    }

    public void setId_guest(String id_guest1) {
        this.id_guest = id_guest1;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop1) {
        this.id_shop = id_shop1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBeOver() {
        return beOver;
    }

    public void setBeOver(String beOver) {
        this.beOver = beOver;
    }

    public String getBeDeliver() {
        return beDeliver;
    }

    public void setBeDeliver(String beDeliver) {
        this.beDeliver = beDeliver;
    }

    public double getTotal_price(){
        return total_price;
    }

    public void setTotal_price(double total_price1){
        this.total_price=total_price1;
    }
}
