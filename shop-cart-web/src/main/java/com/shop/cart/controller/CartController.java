package com.shop.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {

    @RequestMapping("/success")
    public String index(){
        return "success";
    }

    @RequestMapping("/addToCart")
    public String addToCart(int num,String skuId){
        return "redirect:/success";

    }
}
