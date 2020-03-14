package com.shop.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.shop.bean.PmsProductSaleAttr;
import com.shop.bean.PmsSkuInfo;
import com.shop.bean.PmsSkuSaleAttrValue;
import com.shop.service.SkuService;
import com.shop.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    @RequestMapping("/{skuId}.html")
    public String item(@PathVariable String skuId, Map<String,Object> map){
        PmsSkuInfo pmsSkuInfo = skuService.getSkuBySkuId(skuId);
        map.put("skuInfo",pmsSkuInfo);
        List<PmsProductSaleAttr> spuSaleAttrList = spuService.getSpuSaleAttrList(pmsSkuInfo.getProductId(),pmsSkuInfo.getId());
        map.put("spuSaleAttrListCheckBySku",spuSaleAttrList);
        List<PmsSkuInfo> skuInfos = skuService.getSkuBySpuId(pmsSkuInfo.getProductId());
        Map<String,String> map1 = new HashMap<>();
        for(PmsSkuInfo skuInfo : skuInfos){
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = skuInfo.getPmsSkuSaleAttrValueList();
            String k = "";
            for (PmsSkuSaleAttrValue p : pmsSkuSaleAttrValueList) {
                k += p.getSaleAttrValueId() + "|";
            }
            map1.put(k,skuInfo.getId());
        }
        map.put("hashs",JSON.toJSONString(map1));
        return "item";
    }
}
