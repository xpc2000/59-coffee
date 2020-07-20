package com.code59.caffemall.controller.Order.staticVar;

import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.controller.Order.tempVar.OrderSingle;

import java.util.ArrayList;
import java.util.List;

//所有订单
public class OrdersAll {
    static public ArrayList<OrderFinal> orderList=new ArrayList<>();
    static public boolean addOrder(OrderSingle ord)
    {
        //如果orderList中有当前客户点的餐品，就加入其中
        for(OrderFinal f:orderList)
        {
            if(f.getGuestId().equals(ord.getGuestId()))
            {
                f.getCartsAll().add(ord.getCart());//将订单中的某一项加到订单总和中
                return true;
            }
        }

        //否则，新建一个orderFinal
        List<Cart> temp=new ArrayList<>();
        temp.add(ord.getCart());
        OrderFinal orderFinal=new OrderFinal();
        orderFinal.setCartsAll(temp);
        orderFinal.setGuestId(ord.getGuestId());
        orderFinal.setShopId(ord.getShopId());
        orderList.add(orderFinal);
        return false;
    }
    static public OrderFinal getOrderByGuestId(String guestId)
    {
        for(OrderFinal f:orderList)
        {
            if(f.getGuestId().equals(guestId))
                return f;
        }
        return null;
    }
    static public boolean deleteOrder(String guestId)
    {
        if(getOrderByGuestId(guestId)==null||getOrderByGuestId(guestId).equals(null))
        {
            return false;
        }
        else
        {
            orderList.remove(guestId);
            return true;
        }
    }
}
