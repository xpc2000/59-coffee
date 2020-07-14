package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.*;
import com.code59.caffemall.dao.OrderDetailDao;
import com.code59.caffemall.dao.OrderShopDao;
import com.code59.caffemall.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
@DS("coffee_shop")
public class OrderServiceImpl implements OrderServices {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    OrderShopDao orderShopDao;


    @Override
    public Order_shop add(List<Cart> cartList, String id_shop, Guest guest, Discount discount){

        String s = UUID.randomUUID().toString();
        LocalTime time=LocalTime.now();
        LocalTime deliverytime=time.plusMinutes(30);
        String address;
        String phone;
        int state=1;
        double sum=0;
        String id_guest = null;
        Order_detail entry=new Order_detail();
        Order_shop entry_plus=new Order_shop();
        Iterator<Cart> cartIterator=cartList.iterator();

        while(cartIterator.hasNext()){
            Cart cart=cartIterator.next();
            sum+=cart.getPriceAfterDiscount();
        }
        //折扣管理尚未添加，正在施工中
        if(sum>=discount.getPayments_1()&&sum<discount.getPayments_2())
            sum-=discount.getMinus_1();
        else if(sum>=discount.getPayments_2()&&sum<discount.getPayments_3())
            sum-=discount.getminus_2();
        else if(sum>=discount.getPayments_3())
            sum-=discount.getMinus_3();

        //guest=guestDao.selectById(cartList.get(0).getId_guest());
        String time_=deliverytime.toString();
        entry_plus.setId(s);
        entry_plus.setBeDeliver("0");
        entry_plus.setBeOver("0");
        entry_plus.setDeliverAddress(guest.getDelivery_address());
        entry_plus.setIdGuest(guest.getId());
        entry_plus.setIdShop(id_shop);
        entry_plus.setOrderType("0");
        entry_plus.setPhone(guest.getPhone());
        entry_plus.setTime(time_);
        entry_plus.setTotalPrice(sum);
        state=orderShopDao.insert(entry_plus);

        for(int i=0;i<cartList.size()&&state==1;i++)
        {
            entry.setId_food(cartList.get(i).getIdFood());
            entry.setSingle_price(cartList.get(i).getPriceAfterDiscount());
            entry.setNum(cartList.get(i).getNumber());
            entry.setTotal_price(sum);
            entry.setId_order(s);
            state=orderDetailDao.insert(entry);
        }
        return entry_plus;

    }


    @Override
    public int update(Order_shop order){
        return orderShopDao.updateById(order);
    }

    @Override
    public int delete(String id){
        Map<String,Object>columMap=new HashMap<String,Object>();
        columMap.put("id_order",id);
        orderDetailDao.deleteByMap(columMap);
        return orderShopDao.deleteById(id);

    }

    @Override
    public Order_shop get(String id){
        return orderShopDao.selectById(id);
    }

    @Override
    public List<Order_detail> show(String id){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id_order",id);
        return orderDetailDao.selectObjs(wrapper);
    }
}
