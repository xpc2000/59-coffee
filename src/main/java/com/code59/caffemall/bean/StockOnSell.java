package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_onsell")
public class StockOnSell {
    private String idfood;
    private String name;
    private String type;
    private double price;
    private double discount;
    private boolean sellOut;
    private String url;

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String id_food) {
        this.idfood = id_food;
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

    public boolean isSellOut() {
        return sellOut;
    }

    public void setSellOut(boolean sell_out) {
        this.sellOut = sell_out;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
