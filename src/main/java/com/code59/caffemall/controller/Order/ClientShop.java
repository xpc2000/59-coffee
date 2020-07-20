package com.code59.caffemall.controller.Order;

import com.alibaba.fastjson.JSON;
import com.code59.caffemall.bean.Order_detail;
import com.code59.caffemall.bean.Order_shop;
import com.code59.caffemall.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientShop {
    @Autowired
    OrderServices orderServices;
    @RequestMapping("/showorders")
    public String showorders(@RequestBody String guestId)
    {
        List<Order_detail>orders=orderServices.show(guestId);
        return JSON.toJSONString(orders);
    }
    @RequestMapping("/delever")
    public String delever(@RequestBody String orderShopId)
    {
        Order_shop orderShopTemp = orderServices.get(orderShopId);
        orderShopTemp.setDeliverAddress("y");
        if(orderServices.update(orderShopTemp)==1)
            return "ok";
        else
            return "fail";
    }
}
