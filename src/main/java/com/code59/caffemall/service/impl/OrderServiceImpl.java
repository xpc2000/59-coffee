package com.code59.caffemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.bean.Order;
import com.code59.caffemall.bean.Order_detail;
import com.code59.caffemall.dao.GuestDao;
import com.code59.caffemall.dao.OrderDao;
import com.code59.caffemall.dao.OrderDetailDao;
import com.code59.caffemall.dao.OrderShopDao;
import com.code59.caffemall.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.*;

public class OrderServiceImpl implements OrderServices {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    OrderShopDao orderShopDao;

    @Autowired
    GuestDao guestDao;

    @Override
    public Order add(List<Cart> cartList, String id_shop){

        String s = UUID.randomUUID().toString();
        LocalTime time=LocalTime.now();
        LocalTime deliverytime=time.plusMinutes(30);
        String address;
        String phone;
        int state=1;
        double sum=0;
        String id_guest = null;
        Order_detail entry=new Order_detail();
        Order entry_plus=new Order();
        id_guest=cartList.get(0).getId_guest();
        Iterator<Cart> cartIterator=cartList.iterator();

        while(cartIterator.hasNext()){
            Cart cart=cartIterator.next();
            sum+=cart.getPrice_after_discount();
        }
        //折扣管理尚未添加，正在施工中

        Guest guest= guestDao.selectById(id_guest);
        String time_=deliverytime.toString();
        entry_plus.setId(s);
        entry_plus.setBeDeliver("0");
        entry_plus.setBeOver("0");
        entry_plus.setDeliverAddress(guest.getDelivery_address());
        entry_plus.setId_guest(id_guest);
        entry_plus.setId_shop(id_shop);
        entry_plus.setOrderType("0");
        entry_plus.setPhone(guest.getPhone());
        entry_plus.setTime(time_);
        entry_plus.setTotal_price(sum);
        state=orderDao.insert(entry_plus);


        for(int i=0;i<cartList.size()&&state==1;i++)
        {
            entry.setId_food(cartList.get(i).getId_food());
            entry.setSingle_price(cartList.get(i).getPrice_after_discount());
            entry.setNum(cartList.get(i).getNumber());
            entry.setTotal_price(sum);
            entry.setId_order(s);
            state=orderDetailDao.insert(entry);
        }
        return entry_plus;

    }


    @Override
    public int update(Order order){
        return orderDao.updateById(order);
    }

    @Override
    public int delete(String id){
        Map<String,Object>columMap=new HashMap<String,Object>();
        columMap.put("id_order",id);
        orderDetailDao.deleteByMap(columMap);
        return orderDao.deleteById(id);

    }

    @Override
    public Order get(String id){
        return orderDao.selectById(id);
    }

    @Override
    public List<Order_detail> show(String id){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id_order",id);
        return orderDetailDao.selectObjs(wrapper);
    }
}
