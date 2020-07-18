package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_shop")
public class ShopStock {
    private String idFood;
    private int num;

    public String getIdfood() {
        return idFood;
    }

    public void setIdfood(String id_food) {
        this.idFood = id_food;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
