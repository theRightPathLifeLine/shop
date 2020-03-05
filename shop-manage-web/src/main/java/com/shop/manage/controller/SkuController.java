package com.shop.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.PmsSkuInfo;
import com.shop.service.SkuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SkuController {

    @Reference
    SkuService skuService;

    @RequestMapping("/saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }
}
