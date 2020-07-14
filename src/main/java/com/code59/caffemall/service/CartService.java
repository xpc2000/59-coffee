package com.code59.caffemall.service;

import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.bean.Food;

import java.util.List;

public interface CartService {
    public int add(Cart cart, Food food);
    public int delete(String id_guest,String id_food);
    public int update(Cart cart);
    public int clear(String id_guest);
    public List<Cart> get(String id_guest,String id_food);
    public List<Cart> list();
}
