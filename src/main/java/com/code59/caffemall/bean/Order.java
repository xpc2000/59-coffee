package com.code59.caffemall.bean;

public class Order {
    private String id;
    private String idGuest;
    private String orderType;
    private String time;
    private String deliverAddress;
    private String idShop;
    private String phone;
    private String beOver;
    private String beDeliver;
    private double totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_guest() {
        return idGuest;
    }

    public void setId_guest(String id_guest1) {
        this.idGuest = id_guest1;
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

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String id_shop1) {
        this.idShop = id_shop1;
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
        return totalPrice;
    }

    public void setTotal_price(double total_price1){
        this.totalPrice=total_price1;
    }
}
