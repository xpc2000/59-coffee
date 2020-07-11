package com.code59.caffemall.bean;
import com.baomidou.mybatisplus.annotation.TableName;
@TableName(value="shopping_cart")
public class Cart {

    private String id_guest;
    private String id_food;
    private String name;
    private String type;
    private int number;
    private double price_after_discount;

    public String getId_guest(){
        return id_guest;
    }

    public void setId_guest(String id_guest1){
        this.id_guest=id_guest1;
    }

    public String getId_food(){
        return id_food;
    }

    public void setId_food(String id_food1){
        this.id_food=id_food1;
    }

    public String getName(){
        return name;
    }

    public void setName(String name1){
        this.name=name1;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number1){
        this.number=number1;
    }

    public double getPrice_after_discount(){
        return price_after_discount;
    }

    public void setPrice_after_discount(double price_after_discount1) {
        this.price_after_discount=price_after_discount1;
    }

    public String getType(){
        return type;
    }

    public void setType(String type1){
        this.type=type1;
    }

}

