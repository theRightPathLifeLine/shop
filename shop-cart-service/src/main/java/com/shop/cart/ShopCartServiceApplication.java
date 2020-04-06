package com.shop.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.shop.cart.mapper")
@SpringBootApplication
public class ShopCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopCartServiceApplication.class, args);
	}

}
