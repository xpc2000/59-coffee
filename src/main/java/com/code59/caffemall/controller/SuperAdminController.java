package com.code59.caffemall.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.code59.caffemall.bean.DataSourceDTO;
import com.code59.caffemall.service.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;

@RestController
public class SuperAdminController {

    @Autowired
    ShopService shopService;
    @Autowired
    DataSource dataSource;

    @Autowired
    DataSourceCreator dataSourceCreator;
    /*忽略这个警告，实际上是可以正常运行的*/



    @RequestMapping("/test")
    public String addShop()//处理动态数据源的功能，可用来增加数据源
    {
        DataSourceDTO dto = new DataSourceDTO();
        dto.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dto.setPassword("");//输入你的数据库的密码
        dto.setPollName("test");//输入你想要的数据源名字，为shop1,shop2...
        dto.setUrl("jdbc:mysql://localhost:3306/coffee_guest?serverTimezone=UTC");//配置数据源URL
        dto.setUsername("root");//输入你的密码
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPollName(), dataSource);
        System.out.println(ds.getCurrentDataSources().keySet());
        return "ok";
    }

    /*删除数据源，随着删除店铺把数据源也删除*/
    /**/
}
