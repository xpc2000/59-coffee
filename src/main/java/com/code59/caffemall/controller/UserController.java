package com.code59.caffemall.controller;

import com.alibaba.fastjson.JSON;
import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
/*
* 完成用户登陆后的信息编辑功能
* */

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/EditMessage")
    public String updateGuest(@RequestBody Guest guest)
    {
        if(userService.update(guest)==1)
        {
            return "ok";
        }else {
            return "fail";
        }
    }
    @RequestMapping("/getMessage")
    public String getMessage(@RequestBody String id)
    {
        System.out.println(id);
        id = id.substring(0,id.length()-1);
        Guest guest = userService.get(id);
        ArrayList<Guest> tmp = new ArrayList<Guest>();
        tmp.add(guest);
        return JSON.toJSONString(tmp);
    }
}
