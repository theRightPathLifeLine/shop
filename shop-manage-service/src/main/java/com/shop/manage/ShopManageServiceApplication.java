package com.shop.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@EnableCaching
@MapperScan("com.shop.manage.mapper")
@SpringBootApplication
public class ShopManageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopManageServiceApplication.class,args);
    }
}
