package com.code59.caffemall.service;

import com.code59.caffemall.bean.*;

import java.util.List;

public interface OrderServices {
    public Order_shop add(List<Cart> cartList , String id_shop, Guest guest, Discount discount);
    public int update(Order_shop order);
    public int delete(String id);
    public Order_shop get(String id);
    public List<Order_detail> show(String id);

}
