package com.shop.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.PmsBaseAttrInfo;
import com.shop.bean.PmsBaseAttrValue;
import com.shop.service.AttrService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class AttrController {

    @Reference
    AttrService attrService;

    @RequestMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> getAttrInfo(String catalog3Id){
        return attrService.getAttInfo(catalog3Id);
    }

    @RequestMapping("/getAttrValueList")
    public List<PmsBaseAttrValue> getAttrValue(String attrId){
        return attrService.getAttValue(attrId);
    }

    @RequestMapping("/saveAttrInfo")
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        attrService.save(pmsBaseAttrInfo);
        return "success";
    }

}
