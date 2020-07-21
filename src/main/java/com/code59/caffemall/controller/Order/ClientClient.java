package com.code59.caffemall.controller.Order;

import com.alibaba.fastjson.JSON;
import com.code59.caffemall.bean.*;
import com.code59.caffemall.controller.Discount.DiscountController;
import com.code59.caffemall.controller.Order.staticVar.OrderFinal;
import com.code59.caffemall.controller.Order.staticVar.OrdersAll;
import com.code59.caffemall.controller.Order.tempVar.OrderSingle;
import com.code59.caffemall.service.OrderServices;
import com.code59.caffemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Temp{
    private Cart itemm;
    private String user;

    public Cart getItemm() {
        return itemm;
    }

    public void setItemm(Cart itemm) {
        this.itemm = itemm;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
@RestController
public class ClientClient {
    //前期准备：提取出所有门店
    @Autowired
    OrderServices orderServices;
    @Autowired
    UserService userService;
    @RequestMapping("/showOrder")
    public String showorder(String guestId)
    {
        return JSON.toJSONString(OrdersAll.getOrderByGuestId(guestId).getCartsAll());
    }
    @RequestMapping("/addOrder")//提交购物车
    public String addorder(@RequestBody OrderFinal order)
    {
        String guestId= order.getGuestId();
        Guest gtemp=userService.get(guestId);
        orderServices.add(order.getCartsAll(),order.getShopId(),gtemp,DiscountController.discount);
        OrdersAll.deleteOrder(order.getGuestId());
        return "ok";
    }
    //SerchFood功能在superAdmin中已经有了
    @RequestMapping("/addOrderFood")//下单（给某个餐品下单）
    public String addorderfood(@RequestBody OrderSingle temp)//Temp包含Food变量和String(guest_id)变量
    {
        OrdersAll.addOrder(temp);
        return "ok";
    }
}
