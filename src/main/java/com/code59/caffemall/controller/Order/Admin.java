package com.code59.caffemall.controller.Order;

import com.alibaba.fastjson.JSON;
import com.code59.caffemall.bean.Order_shop;
import com.code59.caffemall.bean.Shop;
import com.code59.caffemall.controller.Order.tempVar.OrderSearchByTime;
import com.code59.caffemall.service.OrderServices;
import com.code59.caffemall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Admin {
    @Autowired
    OrderServices orderServices;
    @Autowired
    ShopService shopService;
    @RequestMapping("/SearchOrder")
    public String Searchorder(@RequestBody OrderSearchByTime os) {
        List<Order_shop> ordershops = orderServices.showOrderShops(os);
        List<TempOrder> tempOrderList = convert(ordershops);

        return JSON.toJSONString(tempOrderList);
    }
    @RequestMapping("/SelectStore")
    public String selectstore()
    {
        List<Shop> li=shopService.list();
        li.forEach(l->{
            System.out.println("l\n\n\n"+l);
        });
        return JSON.toJSONString(shopService.list());
    }


    public List<TempOrder> convert(List<Order_shop> ords) {
        List<TempOrder> temporders = new ArrayList<>();
        System.out.println((temporders==null)+"temporderisnullornot");
        for(int i=0;i<ords.size();i++) {
            Shop shop = shopService.get(ords.get(i).getIdShop());
            Order_shop o = ords.get(i);
            String tempname = shop.getName();
            TempOrder to = new TempOrder(o.getId(), o.getIdShop(), tempname, o.getTotalPrice());
            temporders.add(to);
        }
        return temporders;
    }
}

@RestController
class TempOrder {
    private String orderID;
    private String storeID;
    private String name;
    private double money;


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "TempOrder{" +
                "orderID='" + orderID + '\'' +
                ", storeID='" + storeID + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public TempOrder() {
    }

    public TempOrder(String orderID, String storeID, String name, double money) {
        this.orderID = orderID;
        this.storeID = storeID;
        this.name = name;
        this.money = money;
    }
}
