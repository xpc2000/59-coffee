package com.code59.caffemall.bean;

public class Collection {
    private String idGuest;
    private String idFood;
    private String name;
    private String type;
    private double price;
    private double discount;
    private String sellout;

    public String getIdGuest(){
        return idGuest;
    }

    public void setIdGuest(String id_guest1){
        this.idGuest=id_guest1;
    }

    public String getName(){
        return name;
    }

    public void setName(String name1){
        this.name=name1;
    }

    public String getType(){
        return type;
    }

    public void setType(String type1){
        this.type=type1;
    }

    public String getIdFood(){
        return idFood;
    }

    public void setIdFood(String id_food1){
        this.idFood=id_food1;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price1){
        this.price=price1;
    }
    public double getDiscount(){
        return discount;
    }

    public void setDiscount(double discount1){
        this.discount=discount1;
    }

    public String getSellout(){
        return sellout;
    }

    public void setSellout(String sellout1){
        this.sellout=sellout1;
    }
}
