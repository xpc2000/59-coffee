package com.code59.caffemall.bean;

public class Order_detail {
    private String idOrder;
    private String idFood;
    private int num;
    private double singlePrice;
    private double totalPrice;

    public String getId_order(){
        return idOrder;
    }

    public void setIdOrder(String id_order1){
        this.idOrder=id_order1;
    }

    public String getIdFood(){
        return idFood;
    }

    public void setId_food(String id_food1){
        this.idFood=id_food1;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num1) {
        this.num=num1;
    }

    public double getSinglePrice(){
        return singlePrice;
    }

    public void setSinglePrice(double single_price1){
        this.singlePrice=single_price1;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(double total_price1){
        this.totalPrice=total_price1;
    }
}


