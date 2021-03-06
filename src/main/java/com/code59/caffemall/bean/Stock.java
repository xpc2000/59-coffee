package com.code59.caffemall.bean;

public class Stock {
    private String idFood;
    private String idShop;
    private String name;
    private String type;
    private double price;
    private double discount;
    private String sellOut;
    private String url;
    private int num;

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
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

    public String getSellOut() {
        return sellOut;
    }

    public void setSellOut(String sellOut) {
        this.sellOut = sellOut;
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

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "idFood='" + idFood + '\'' +
                ", idShop='" + idShop + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", sellOut='" + sellOut + '\'' +
                ", url='" + url + '\'' +
                ", num=" + num +
                '}';
    }

    public Stock() {
    }

    public Stock(String idFood, String idShop, String name, String type, double price, double discount, String sellOut, String url, int num) {
        this.idFood = idFood;
        this.idShop = idShop;
        this.name = name;
        this.type = type;
        this.price = price;
        this.discount = discount;
        this.sellOut = sellOut;
        this.url = url;
        this.num = num;
    }
}
