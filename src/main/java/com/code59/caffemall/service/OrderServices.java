package com.code59.caffemall.service;

import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.bean.Order;
import com.code59.caffemall.bean.Order_detail;

import java.util.List;

public interface OrderServices {
    public Order add(List<Cart> cartList ,String id_shop);
    public int update(Order order);
    public int delete(String id);
    public Order get(String id);
    public List<Order_detail> show(String id);

}
