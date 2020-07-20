package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.Discount;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.bean.Order;
import com.code59.caffemall.dao.FoodDao;
import com.code59.caffemall.dao.OrderDao;
import com.code59.caffemall.dao.OrderDetailDao;
import com.code59.caffemall.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@DS("master")
public class MoneyServiceImpl implements MoneyService {
    @Autowired
    FoodDao foodDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderDetailDao orderDetailDao;

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
        List<Order> result = new ArrayList<>();;
        for(int i=0;i<orderList.size();i++)
        {
            LocalDateTime localDateTime = LocalDateTime.parse(orderList.get(i).getTime(), timeDtf);
            if(localDateTime.isAfter(start)&&localDateTime.isBefore(end))
                result.add(orderList.get(i));
        }

        return result;
    }

    @Override
    public void quicksort(List<String> listOfId,List<Integer> listOfNum,int low,int high){
        int pivot, p_pos, i, t;
        String tem;
        if(low<high)
        {
            p_pos = low;
            pivot=listOfNum.get(p_pos);
            for (i = low + 1; i <= high; i++)
            {
                if(listOfNum.get(p_pos)>pivot) {
                    p_pos++;
                    t = listOfNum.get(p_pos);
                    listOfNum.set(p_pos, listOfNum.get(i));
                    listOfNum.set(i, t);
                    tem = listOfId.get(p_pos);
                    listOfId.set(p_pos, listOfId.get(i));
                    listOfId.set(i, tem);
                }
            }
            t=listOfNum.get(low);
            listOfNum.set(low,listOfNum.get(p_pos));
            listOfNum.set(p_pos,t);
            tem=listOfId.get(low);
            listOfId.set(low,listOfId.get(p_pos));
            listOfId.set(p_pos,tem);
            quicksort(listOfId,listOfNum,low,p_pos-1);
            quicksort(listOfId,listOfNum,p_pos+1,high);
        }
    }

    @Override
    public List<Food> ranking(){
        List<Food> foodList=foodDao.selectList(null);
        List<Integer> numList=new ArrayList<>();
        List<String> result_id=new ArrayList<>();
        int num=0;
        Food intermedia=new Food();
        QueryWrapper wrapper=new QueryWrapper();

        for(int i=0;i<foodList.size();i++)
        {
            intermedia=foodList.get(i);
            wrapper.eq("id_food",intermedia.getId());
            numList.add(orderDetailDao.selectCount(wrapper));
            result_id.add(intermedia.getId());
        }
        quicksort(result_id,numList,0,foodList.size()-1);
        Collections.reverse(result_id);
        Collections.reverse(numList);

        for(int i=0;i<result_id.size();i++)
        {
            foodList.set(i,foodDao.selectById(result_id.get(i)));
        }

        return foodList;
    }

}