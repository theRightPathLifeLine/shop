package com.shop.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.shop.user.mapper")
@SpringBootApplication
public class ShopUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopUserServiceApplication.class, args);
	}

}
