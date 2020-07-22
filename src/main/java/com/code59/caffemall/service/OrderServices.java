package com.code59.caffemall.service;

import com.code59.caffemall.bean.*;
import com.code59.caffemall.controller.Order.tempVar.OrderGenerating;
import com.code59.caffemall.controller.Order.tempVar.OrderSearchByTime;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderServices {
    public List<String> add(List<Cart> cartList , Guest guest, OrderGenerating og, Discount discount);
    public int update(Order_shop order);
    public int delete(String id);
    public Order_shop get(String id);
    public List<Order_detail> show(String id);
    public List<Order_shop> showOrderShops(OrderSearchByTime os);
    public List<Order_shop> showOrderShops(String shopid);
    public List<Order_shop> showOrderShopsClient(String guestid);
}
