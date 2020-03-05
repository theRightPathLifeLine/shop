package com.shop.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.PmsBaseCatalog1;
import com.shop.bean.PmsBaseCatalog2;
import com.shop.bean.PmsBaseCatalog3;
import com.shop.service.CatalogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1List(){
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogService.getCatalog1();
        return pmsBaseCatalog1s;
    }

    @RequestMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2List(String catalog1Id){
        return catalogService.getCatalog2(catalog1Id);
    }

    @RequestMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3List(String catalog2Id){
        return catalogService.getCatalog3(catalog2Id);
    }
}
