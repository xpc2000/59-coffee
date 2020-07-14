package com.code59.caffemall.bean;
import com.baomidou.mybatisplus.annotation.TableName;
@TableName(value="shopping_cart")
public class Cart {

    private String idGuest;
    private String idFood;
    private String name;
    private String type;
    private int number;
    private double priceAfterDiscount;

    public String getIdGuest(){
        return idGuest;
    }

    public void setIdGuest(String id_guest1){
        this.idGuest=id_guest1;
    }

    public String getIdFood(){
        return idFood;
    }

    public void setIdFood(String id_food1){
        this.idFood=id_food1;
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

    public double getPriceAfterDiscount(){
        return priceAfterDiscount;
    }

    public void setPrice_after_discount(double price_after_discount1) {
        this.priceAfterDiscount=price_after_discount1;
    }

    public String getType(){
        return type;
    }

    public void setType(String type1){
        this.type=type1;
    }

}

