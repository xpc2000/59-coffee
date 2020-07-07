package com.code59.caffemall.controller;

import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.bean.Shop;
import com.code59.caffemall.service.UserService;
import com.code59.caffemall.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController
{
    @Autowired
    UserService userService;
    @RequestMapping("/AdminLogin")
    public String login()
    {
        return "ok";
    }

    @RequestMapping("/GuestLogin")
    public String login(@RequestBody Guest guest)
    {
        System.out.println(guest);
        List<Guest> guests = userService.getUserByMessage(guest.getId(),guest.getPassword());
        if(null==guests||guests.size()==0)
        {
            return "fall";
        }else{
            return "ok";
        }
    }

    @RequestMapping("/ShopAdminLogin")
    public String login(@RequestBody Shop shop)
    {

        List<Shop> shops = userService.getAdminByMessage(shop.getId(),shop.getPassword());//name->id
        if(null==shops||shops.size()==0)
        {
            return "fall";
        }else
        {
            return "ok";
        }
    }
    @RequestMapping("/GuestRegister")
    public String Register(@RequestBody Guest guest)
    {
        System.out.println(guest);
        Guest t = userService.get(guest.getId());
        if(t==null)
        {
            userService.add(guest);
            return "ok";
        }else {
            return "the id is already used by others";
        }
    }
}
