package com.code59.caffemall.bean;

public class Stock {
    private String idFood;
    private String name;
    private String type;
    private double price;
    private double discount;
    private String sellOut;
    private String url;
    private int num;

    public String getIdfood() {
        return idFood;
    }

    public void setIdfood(String id_food) {
        this.idFood = id_food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String isSellOut() {
        return sellOut;
    }

    public void setSellOut(String sell_out) {
        this.sellOut = sell_out;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
