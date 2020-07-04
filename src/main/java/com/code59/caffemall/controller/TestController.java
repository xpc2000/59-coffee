package com.code59.caffemall.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @RequestMapping("/AdminLogin")
    public String test()
    {
        return "ok";
    }
}
