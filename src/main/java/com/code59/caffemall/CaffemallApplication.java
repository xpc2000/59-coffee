package com.code59.caffemall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.code59.caffemall.dao")
public class CaffemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaffemallApplication.class, args);
    }

}
