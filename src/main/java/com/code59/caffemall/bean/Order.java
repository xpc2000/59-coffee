package com.code59.caffemall.bean;

public class Order {
    private String id;
    private String idFood;
    private int num;
    private boolean orderType;
    private String time;
    private String deliverAddress;
    private String idGuest;
    private String phone;
    private boolean beOver;
    private boolean beDeliver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean getOrderType() {
        return orderType;
    }

    public void setOrderType(boolean orderType) {
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

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getBeOver() {
        return beOver;
    }

    public void setBeOver(boolean beOver) {
        this.beOver = beOver;
    }

    public boolean getBeDeliver() {
        return beDeliver;
    }

    public void setBeDeliver(boolean beDeliver) {
        this.beDeliver = beDeliver;
    }
}
