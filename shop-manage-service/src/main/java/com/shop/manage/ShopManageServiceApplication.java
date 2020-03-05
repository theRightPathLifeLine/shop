package com.shop.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.shop.manage.mapper")
@SpringBootApplication
public class ShopManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManageServiceApplication.class,args);
    }
}
