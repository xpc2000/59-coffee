package com.code59.caffemall.bean;

public class Order_detail {
    private String idOrder;
    private String idFood;
    private int num;
    private double singlePrice;
    private double totalPrice;

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
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

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order_detail{" +
                "idOrder='" + idOrder + '\'' +
                ", idFood='" + idFood + '\'' +
                ", num=" + num +
                ", singlePrice=" + singlePrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}


