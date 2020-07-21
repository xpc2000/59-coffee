package com.code59.caffemall.service;

import com.code59.caffemall.bean.Food;
import com.code59.caffemall.bean.StockOnSell;

import java.util.List;

public interface MenuService {
    public int add(Food food);
    public int delete(String id);
    public int update(Food food);
    public Food get(String id);
    public List<Food> list();
    public int count();
    public List<Food> getFoodByType(String type);
    public List<Food> getFoodByName(String name);

}
