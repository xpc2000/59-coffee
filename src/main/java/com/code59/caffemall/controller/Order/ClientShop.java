package com.code59.caffemall.controller.Order;

import com.alibaba.fastjson.JSON;
import com.code59.caffemall.bean.Order_detail;
import com.code59.caffemall.bean.Order_shop;
import com.code59.caffemall.controller.Order.tempVar.IdAndId;
import com.code59.caffemall.controller.Order.tempVar.NameAndId;
import com.code59.caffemall.service.OrderServices;
import com.code59.caffemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientShop {
    @Autowired
    OrderServices orderServices;
    @Autowired
    UserService userService;
//    @RequestMapping("/SearchShopOrder1111111")
//    public String showorders(@RequestBody String guestId)
//    {
//        List<Order_detail>orders=orderServices.show(guestId);
//        return JSON.toJSONString(orders);
//    }

    @RequestMapping("/SearchShopOrder")
    public String SearchShoporder(@RequestBody String shopid) {
        System.out.println(shopid + "shopid");
        shopid = shopid.substring(0, shopid.length() - 1);
        List<Order_shop> ordershoplist = orderServices.showOrderShops(shopid);
        List<OrderTemp> list = convertToOrderTemp(ordershoplist);
        list.forEach(t -> {
            System.out.println(t);
        });
        if (list == null) return null;
        return JSON.toJSONString(list);
//        List<Order_shop>list=orderServices.showOrderShops(shopid);
//        return JSON.toJSONString(list);
    }

    @RequestMapping("/Deliver")
    public String delever(@RequestBody String orderId) {
        orderId = orderId.substring(0, orderId.length() - 1);
        System.out.println(orderId + "\n\n\nordersid");
        Order_shop orderShopTemp = orderServices.get(orderId);
        if (orderShopTemp != null) {
            orderShopTemp.setBeDeliver("y");
        }
        if (orderServices.update(orderShopTemp) == 1)
            return "ok";
        else
            return "fail";

    }

    @RequestMapping("/OrderDetail")
    public String orderdetail(@RequestBody String orderId) {
        orderId="ec5555sae";
        List<Order_detail>order_details=orderServices.show(orderId);
        System.out.println("aaaaaaaaaaaaa\n\n\n");
        System.out.println("aaaaaaaaaaaaaaaa"+order_details.get(0));
//        Order_detail order_detail=order_details.get(0);
//        System.out.println("1");
//        //order_details=null;
//        for(int i=0;i<order_details.size();i++)
//        {
//            System.out.println(order_details.get(i).getIdFood()+"food");
//            System.out.println(order_details.get(i).getIdOrder()+"order");
//        }
        System.out.println("1");
        order_details.forEach(order_detail -> {
            System.out.println(order_detail);
        });
        System.out.println("2");
        return JSON.toJSONString(order_details);
    }


    public List<OrderTemp> convertToOrderTemp(List<Order_shop> orderdetails) {
        if (orderdetails == null)
            return null;
        List<OrderTemp> orderTemps = new ArrayList<>();
        orderdetails.forEach(order_shop -> {
            String guestName = userService.get(order_shop.getIdGuest()).getName();
            OrderTemp ot = new OrderTemp(order_shop.getId(), order_shop.getTime(), guestName, order_shop.getDeliverAddress(), order_shop.getPhone(),
                    order_shop.getTotalPrice(), order_shop.getBeDeliver(), order_shop.getBeOver());
            orderTemps.add(ot);
        });
        return orderTemps;
    }
}

class OrderTemp {
    private String orderID;
    private String date;
    private String name;
    private String address;
    private String phone;
    private double totalPrice;
    private String beDeliver;
    private String beOver;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getBeDeliver() {
        return beDeliver;
    }

    public void setBeDeliver(String beDeliver) {
        this.beDeliver = beDeliver;
    }

    public String getBeOver() {
        return beOver;
    }

    public void setBeOver(String beOver) {
        this.beOver = beOver;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public OrderTemp(String orderID, String date, String name, String address, String phone, double totalPrice, String beDeliver, String beOver) {
        this.orderID = orderID;
        this.date = date;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.beDeliver = beDeliver;
        this.beOver = beOver;
    }

    @Override
    public String toString() {
        return "OrderTemp{" +
                "orderID='" + orderID + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", totalPrice=" + totalPrice +
                ", beDeliver='" + beDeliver + '\'' +
                ", beOver='" + beOver + '\'' +
                '}';
    }
}
