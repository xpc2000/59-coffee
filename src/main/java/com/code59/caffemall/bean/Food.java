package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.xml.bind.PrintConversionEvent;

/*总数据库中所拥有的食品*/
@TableName(value = "menu_total")
public class Food {
    private String id;
    private String name;
    private String type;
    private double price;
    private double discount;
    private boolean unsell;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean getUnsell() {
        return unsell;
    }

    public void setUnsell(boolean unsell) {
        this.unsell = unsell;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
