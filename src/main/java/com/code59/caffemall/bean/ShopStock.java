package com.code59.caffemall.bean;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stock_shop")
public class ShopStock {
    private String idFood;
    private int num;

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
