package com.code59.caffemall.bean;

public class Order_detail {
    private String id_order;
    private String id_food;
    private int num;
    private double single_price;
    private double total_price;

    public String getId_order(){
        return id_order;
    }

    public void setId_order(String id_order1){
        this.id_order=id_order1;
    }

    public String getId_food(){
        return id_food;
    }

    public void setId_food(String id_food1){
        this.id_food=id_food1;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num1) {
        this.num=num1;
    }

    public double getSingle_price(){
        return single_price;
    }

    public void setSingle_price(double single_price1){
        this.single_price=single_price1;
    }

    public double getTotal_price(){
        return total_price;
    }

    public void setTotal_price(double total_price1){
        this.total_price=total_price1;
    }
}


