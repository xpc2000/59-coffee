package com.code59.caffemall.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.code59.caffemall.bean.DataSourceDTO;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.bean.Shop;
import com.code59.caffemall.service.MenuService;
import com.code59.caffemall.service.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.events.Event;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
完成门店新增，门店修改，门店删除，管理员登陆
 */

@RestController
public class SuperAdminController {

    @Autowired
    ShopService shopService;
    @Autowired
    DataSource dataSource;

    @Autowired
    DataSourceCreator dataSourceCreator;
    /*忽略这个警告，实际上是可以正常运行的*/
    @Autowired
    MenuService menuService;


    @RequestMapping("/storeNew")
    public String addShop(@RequestBody Shop shop)//处理动态数据源的功能，可用来增加数据源
    {
        if(shopService.get(shop.getId())!=null)
        {
            return "fail";
        }
        shopService.add(shop);

        DataSourceDTO dto = new DataSourceDTO();
        String driverName = "com.mysql.cj.jdbc.Driver";
        String baseUrl = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
        String userName="root";
        String password="";
        String poolName= "shop"+shop.getId();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(baseUrl,userName,password);Statement statement = connection.createStatement();) {
            String sql = String.format("CREATE DATABASE %s",poolName);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dto.setDriverClassName(driverName);
        dto.setPassword("");//输入你的数据库的密码
        dto.setPollName(poolName);//输入你想要的数据源名字，为shop1,shop2...
        String url = String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=UTC",poolName);
        dto.setUrl(url);//配置数据源URL
        dto.setUsername("root");//输入你的用户名
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPollName(), dataSource);
        System.out.println(ds.getCurrentDataSources().keySet());
        return "ok";
    }

    /**/
    @RequestMapping("/AdminLogin")
    public String adminLogin()
    {
        return "ok";
    }

    @RequestMapping("/SearchStore")
    public String getShopInfo(@RequestBody String id)
    {
        if(id.equals("null="))
        {
            return JSON.toJSONString(shopService.list());
        }else {
            id = id.substring(0,id.length()-1);
            ArrayList<Shop> temp = new ArrayList<Shop>();
            temp.add(shopService.get(id));
            String ret = JSON.toJSONString(temp);
            System.out.println(ret);
            return ret;
        }
    }

    @RequestMapping("/storeEdit")
    public String updateStore(@RequestBody Shop shop)
    {
        if(shopService.update(shop)==1)
        {
            return "ok";
        }else {
            return "fail";
        }
    }

    @RequestMapping("/storeDelete")
    public String deleteStore(@RequestBody String id)
    {
        System.out.println(id);
        id = id.substring(0,id.length()-1);
        if(shopService.delete(id)==1)
        {
            String name = "shop"+id;
            DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
            ds.removeDataSource(name);
            return "ok";
        }
        return "fall";
    }

    @RequestMapping("/foodNew")
    public String foodNew(@RequestBody Food food)
    {
        food.setUnsell("n");
        if(menuService.add(food)==1)
        {
            return "ok";
        }
        return "false";
    }

    @RequestMapping("/SearchFood")
    public String getFood(@RequestBody String name)
    {
        if(name.equals("null="))
        {
            return JSON.toJSONString(menuService.list());
        }else {
            name = name.substring(0,name.length()-1);
            return JSON.toJSONString(menuService.getFoodByName(name));
        }
    }

    @RequestMapping("/foodDelete")
    public String deleteFood(@RequestBody String id)
    {
        id = id.substring(0,id.length()-1);
        if (menuService.delete(id)==1)
        {
            return "ok";
        }else {
            return "fall";
        }
    }

    @RequestMapping("/foodEdit")
    public String editFood(@RequestBody Food food)
    {
        if(menuService.update(food)==1)
        {
            return "ok";
        }else {
            return "failed";
        }

    }
    /*删除数据源，随着删除店铺把数据源也删除*/
    /**/
}
