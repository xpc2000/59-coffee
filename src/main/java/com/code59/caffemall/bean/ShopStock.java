package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_shop")
public class ShopStock {
    private String idfood;
    private int num;

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String id_food) {
        this.idfood = idfood;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
