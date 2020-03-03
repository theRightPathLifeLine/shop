package com.shop.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.UmsMember;
import com.shop.bean.UmsMemberReceiveAddress;
import com.shop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Reference
    UserService userService;

    @GetMapping("/getAll")
    public List<UmsMember> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getRecevice")
    public List<UmsMemberReceiveAddress> getReceviceByMemberId(String id){
        return userService.getReceviceByMemberId(id);
    }

    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
