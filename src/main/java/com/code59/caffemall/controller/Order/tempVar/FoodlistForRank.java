package com.code59.caffemall.controller.Order.tempVar;

public class FoodlistForRank {
    private String foodId;
    private String foodName;
    private int sales;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public FoodlistForRank(String foodId, String foodName, int sales) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.sales = sales;
    }
}
