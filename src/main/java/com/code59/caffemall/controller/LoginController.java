package com.code59.caffemall.controller;

import com.code59.caffemall.bean.Customer;
import com.code59.caffemall.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    CustomerService customerService;
    @RequestMapping("/login")
    public String login(@RequestBody Customer customer)
    {
        if(customer.getPassWord()==customerService.get(customer.getId()).getPassWord())
        {
            return "";
        }else{
            return "";
        }
    }
}
