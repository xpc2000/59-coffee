package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.dao.CartDao;
import com.code59.caffemall.dao.FoodDao;
import com.code59.caffemall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DS("coffee_guest")
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao cartdao;

    @Autowired
    FoodDao foodDao;

    @Override
    public int add(Cart cart1,Food food){
        //Food food=foodDao.selectById(cart1.getId_food());
        double price=food.getPrice();
        double discount=food.getDiscount();
        cart1.setPrice_after_discount(price*discount);
        return cartdao.insert(cart1);
    }


    @Override
    public int delete(String id_guest1,String id_food1){
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("id_guest",id_guest1);
        columnMap.put("id_food",id_food1);
        return cartdao.deleteByMap(columnMap);
    }

    @Override
    public int update(Cart cart1){
        return cartdao.updateById(cart1);
    }

    @Override
    public int clear(String id_guest){
        Map<String,Object>columMap=new HashMap<String,Object>();
        columMap.put("id_guest",id_guest);
        return cartdao.deleteByMap(columMap);
    }

    @Override
    public List<Cart> get(String id_guest1, String id_food1){
        Map<String,Object>columMap=new HashMap<String,Object>();
        columMap.put("id_guest",id_guest1);
        columMap.put("id_food",id_food1);
        return cartdao.selectByMap(columMap);
    }

    @Override
    public List<Cart> list(){
        return cartdao.selectList(null);
    }

    @Override
    public List<Cart> get(String id_guest) {
        QueryWrapper<Cart>wrapper=new QueryWrapper<>();
        wrapper.eq("id_guest",id_guest);
        return cartdao.selectList(wrapper);
    }
}
