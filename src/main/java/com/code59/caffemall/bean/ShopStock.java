package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_shop")
public class ShopStock {
    private String id_food;
    private int num;

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
