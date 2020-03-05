package com.shop.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.PmsBaseSaleAttr;
import com.shop.bean.PmsProductImage;
import com.shop.bean.PmsProductInfo;
import com.shop.bean.PmsProductSaleAttr;
import com.shop.manage.util.UploadUtil;
import com.shop.service.SpuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class SpuController {

    @Reference
    SpuService spuService;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> getSpuList(String catalog3Id){
        return spuService.getSpuList(catalog3Id);

    }

    @RequestMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> getBaseSaleAttrList(){
        return spuService.getBaseSaleAttrList();
    }

    @RequestMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        return UploadUtil.upload(multipartFile);
    }

    @RequestMapping("/spuImageList")
    public List<PmsProductImage> spuImageList(String spuId){
        return spuService.getSpuImageList(spuId);
    }

    @RequestMapping("/spuSaleAttrList")
    public List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId){
        return spuService.getSpuSaleAttrList(spuId);
    }

}
