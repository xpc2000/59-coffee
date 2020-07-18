package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Discount;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.bean.Order;
import com.code59.caffemall.dao.FoodDao;
import com.code59.caffemall.dao.OrderDao;
import com.code59.caffemall.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public List<Order> moneyflow(String start_time, String end_time){
        //施工中
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(start_time,timeDtf);
        LocalDateTime end = LocalDateTime.parse(end_time,timeDtf);
        List<Order> orderList=orderDao.selectList(null);
        List<Order> result = null;
        for(int i=0;i<orderList.size();i++)
        {
            LocalDateTime localDateTime = LocalDateTime.parse(orderList.get(i).getTime(), timeDtf);
            if(localDateTime.isAfter(start)&&localDateTime.isBefore(end))
                result.add(orderList.get(i));
        }

        return result;
    }

}