package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_onsell")
public class StockOnSell {
    private String id_food;
    private String name;
    private String type;
    private double price;
    private double discount;
    private boolean sell_out;
    private String url;

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
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

    public boolean isSell_out() {
        return sell_out;
    }

    public void setSell_out(boolean sell_out) {
        this.sell_out = sell_out;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
