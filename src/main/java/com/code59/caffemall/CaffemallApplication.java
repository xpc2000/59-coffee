package com.code59.caffemall;

import com.code59.caffemall.bean.Cart;
import com.code59.caffemall.service.OrderServices;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.code59.caffemall.dao")
public class CaffemallApplication {

    public static void main(String[] args) {

        SpringApplication.run(CaffemallApplication.class, args);
    }

}
