package com.code59.caffemall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.*;
import com.code59.caffemall.controller.Order.tempVar.OrderGenerating;
import com.code59.caffemall.controller.Order.tempVar.OrderSearchByTime;
import com.code59.caffemall.dao.OrderDetailDao;
import com.code59.caffemall.dao.OrderShopDao;
import com.code59.caffemall.dao.ShopStockDao;
import com.code59.caffemall.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@DS("coffee_shop")
public class OrderServiceImpl implements OrderServices {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    OrderShopDao orderShopDao;

    @Autowired
    ShopStockDao shopStockDao;

    @Override
    public List<String> add(List<Cart> cartList, Guest guest, OrderGenerating og, Discount discount){

        System.out.println(og);
        String id_shop=og.getStoreId();
        String s = UUID.randomUUID().toString();
        LocalDateTime time=LocalDateTime.now();
        LocalDateTime deliverytime=time.plusMinutes(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        String time_=deliverytime.format(formatter);
        String address=og.getAddress();
        String phone=guest.getPhone();
        ShopStock shopStock=new ShopStock();
        int state=1;
        double sum=0;
        String id_guest = null;
        Order_detail entry=new Order_detail();
        Order_shop entry_plus=new Order_shop();
        List<String> result=new ArrayList<>();;
        Iterator<Cart> cartIterator=cartList.iterator();

        while(cartIterator.hasNext()){
            Cart cart=cartIterator.next();
            //shopStock=shopStockDao.selectById(cart.getIdFood());
            QueryWrapper<ShopStock> wrapper_1=new QueryWrapper<>();
            wrapper_1.eq("id_food",cart.getIdFood());
            shopStock=shopStockDao.selectOne(wrapper_1);
            if(shopStock==null)
            {
                state=0;
                if(result.isEmpty())
                    result.add(0,"Fail");
                result.add(cart.getIdFood());
                result.add("-1");
            }
            else if(cart.getNumber()>shopStock.getNum())
            {
                state=0;
                if(result.isEmpty())
                    result.add(0,"Fail");
                result.add(cart.getIdFood());
                result.add(String.valueOf(shopStock.getNum()));
            }
            else
            {
                sum+=cart.getPriceAfterDiscount();
                shopStock.setNum(shopStock.getNum()-cart.getNumber());
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("id_food",shopStock.getIdfood());
                shopStockDao.update(shopStock,wrapper);
            }
        }

        if(state==0)
            return result;

        else
        {
            if (sum >= discount.getPayments_1() && sum < discount.getPayments_2())
                sum -= discount.getMinus_1();
            else if (sum >= discount.getPayments_2() && sum < discount.getPayments_3())
                sum -= discount.getminus_2();
            else if (sum >= discount.getPayments_3())
                sum -= discount.getMinus_3();

            //guest=guestDao.selectById(cartList.get(0).getId_guest());
            entry_plus.setId(s);
            entry_plus.setBeDeliver("n");
            entry_plus.setBeOver("n");
            entry_plus.setDeliverAddress(og.getAddress());
            entry_plus.setIdGuest(guest.getId());
            entry_plus.setIdShop(id_shop);
            entry_plus.setOrderType(og.getoType());
            entry_plus.setPhone(guest.getPhone());
            entry_plus.setTime(time_);
            entry_plus.setTotalPrice(sum);
            state = orderShopDao.insert(entry_plus);

            for (int i = 0; i < cartList.size() && state == 1; i++) {
                entry.setIdFood(cartList.get(i).getIdFood());
                entry.setSinglePrice(cartList.get(i).getPriceAfterDiscount());
                entry.setNum(cartList.get(i).getNumber());
                entry.setTotalPrice(sum);
                entry.setIdOrder(s);
                state = orderDetailDao.insert(entry);
            }

            result.add(0,"Success");
            result.add(entry_plus.getId());
            result.add(entry_plus.getBeOver());
            result.add(entry_plus.getBeDeliver());
            result.add(entry_plus.getDeliverAddress());
            result.add(entry_plus.getIdGuest());
            result.add(entry_plus.getIdShop());
            result.add(entry_plus.getOrderType());
            result.add(entry_plus.getPhone());
            result.add(entry_plus.getTime());
            result.add(String.valueOf(entry_plus.getTotalPrice()));
            return result;
        }

    }


    @Override
    public int update(Order_shop order){
        return orderShopDao.updateById(order);
    }

    @Override
    public int delete(String id){
        QueryWrapper<Order_detail> columMap1 =new QueryWrapper<Order_detail>();
        columMap1.eq("id_order",id);
        List<Order_detail> list= orderDetailDao.selectList(columMap1);
        for(int i=0;i<list.size();i++)
        {
            ShopStock stock = shopStockDao.selectById(list.get(i).getIdFood());
            stock.setNum(stock.getNum()+list.get(i).getNum());
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("id_order",id);
            shopStockDao.update(stock,wrapper);
        }
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
        QueryWrapper<Order_detail>wrapper=new QueryWrapper<>();
        wrapper.eq("id_order",id);
        return orderDetailDao.selectList(wrapper);
    }

    @Override
    public List<Order_shop> showOrderShops(OrderSearchByTime os) {

        if((os.getDate1()==null||os.getDate1().equals(""))||(os.getDate2()==null)||os.getDate2().equals(""))
        {

            if(os.getStoreID()==null||os.getStoreID().equals(""))
            {
                return orderShopDao.selectList(null);
            }
            QueryWrapper<Order_shop> wrapper=new QueryWrapper<>();
            wrapper.eq("id_shop",os.getStoreID());
            List<Order_shop> order_shops= orderShopDao.selectList(wrapper);
            return order_shops;
        }
        else
        {
            DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //String date1=os.getDate1().substring(0,19);
            String date1=os.getDate1();
            System.out.println(date1);

            date1=date1+" 00:00:00";
            System.out.println("开始0");
            LocalDateTime start = LocalDateTime.parse(date1,timeDtf);
            System.out.println("开始");
            System.out.println(start);
            String date2=os.getDate2();
            date2=date2+" 00:00:00";
//            date2=date2.replace('T',' ');
            LocalDateTime end = LocalDateTime.parse(date2,timeDtf);
            System.out.println(end);
            List<Order_shop> orderList=orderShopDao.selectList(null);
            List<Order_shop> result = new ArrayList<>();;
            for(int i=0;i<orderList.size();i++)
            {
                LocalDateTime localDateTime = LocalDateTime.parse(orderList.get(i).getTime(), timeDtf);
                if(localDateTime.isAfter(start)&&localDateTime.isBefore(end))
                    result.add(orderList.get(i));
            }
            if(os.getStoreID()==null||os.getStoreID().equals(""))
            {
                return result;
            }

            else
            {
                return result;
            }


//            QueryWrapper<Order_shop> wrapper=new QueryWrapper<>();
//            String time1=os.getDate1().substring(0,10);
//            time1.replace('-',':');
//            System.out.println(time1);
//            String time2=os.getDate2().substring(0,10);
//            time1=time1+" 00:00:00";
//            time2=time2+" 00:00:00";
//            time1.replace('-',':');
//            wrapper.eq("id_shop",os.getStoreID())
//                    .between("time",time1,time2);
//            List<Order_shop> order_shops= orderShopDao.selectList(wrapper);
//            return order_shops;
        }
    }

    @Override
    public List<Order_shop> showOrderShops(String shopid) {
        if(shopid==null||shopid.equals(""))
            return orderShopDao.selectList(null);
        else
        {
            QueryWrapper<Order_shop> wrapper=new QueryWrapper<>();
            wrapper.eq("id_shop",shopid);
            return orderShopDao.selectList(wrapper);
        }
    }

    @Override
    public List<Order_shop> showOrderShopsClient(String guestid) {
        QueryWrapper<Order_shop>wrapper=new QueryWrapper<>();
        wrapper.eq("id_guest",guestid);
        return orderShopDao.selectList(wrapper);
    }
}
