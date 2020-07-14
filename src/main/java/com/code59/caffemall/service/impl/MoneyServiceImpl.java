package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Discount;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.dao.FoodDao;
import com.code59.caffemall.dao.OrderDao;
import com.code59.caffemall.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class MoneyServiceImpl implements MoneyService {
    @Autowired
    FoodDao foodDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public int set_single_discount(double discount, String id_food){
        Food food= foodDao.selectById(id_food);
        food.setDiscount(discount);
        return foodDao.updateById(food);
    }

    @Override
    public Discount set_total_discount1(Discount discount,
                                        int payments1,int minus1,
                                        int payments2,int minus2,
                                        int payments3,int minus3){
        discount.setPayments_1(payments1);
        discount.setMinus_1(minus1);
        discount.setPayments_2(payments2);
        discount.setMinus_2(minus2);
        discount.setPayments_3(payments3);
        discount.setMinus_3(minus3);
        return discount;
    }

    @Override
    public  void moneyflow(String start_time, String end_time){
        //施工中

    }



}
