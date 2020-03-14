package com.shop.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.bean.*;
import com.shop.service.SearchService;
import com.shop.service.SpuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ListController {

    @Reference
    SearchService searchService;

    @Reference
    SpuService spuService;

    @RequestMapping("/list.html")
    public String list(SearchParam searchParam, Map<String,Object> map){
        List<SearchSkuInfo> searchData = searchService.getSearchData(searchParam);
        String[] attrValue = searchParam.getValueId();
        List<PmsBaseAttrValue> selectedAttrValueList = new ArrayList<>();
        Set<String> strings = new HashSet<>();
        searchData.stream().forEach(p -> {
            List<PmsSkuAttrValue> pmsSkuAttrValueList = p.getPmsSkuAttrValueList();
            pmsSkuAttrValueList.stream().forEach(pmsSkuAttrValue -> strings.add(pmsSkuAttrValue.getValueId()));
        });
        String join = StringUtils.join(strings, ",");
        List<PmsBaseAttrInfo> byAttrValueId = spuService.getByAttrValueId(join);
        Iterator<PmsBaseAttrInfo> iterator = byAttrValueId.iterator();
        while (iterator.hasNext()){
            List<PmsBaseAttrValue> attrValueList = iterator.next().getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                if(attrValue != null){
                    for (String s : attrValue) {
                        if (s.equals(pmsBaseAttrValue.getId())){
                            pmsBaseAttrValue.setUrlParam(geturlParam(searchParam,s));
                            selectedAttrValueList.add(pmsBaseAttrValue);
                            iterator.remove();
                        }
                    }
                }
            }
        }
        map.put("skuLsInfoList",searchData);
        map.put("keyword",searchParam.getKeyword());
        map.put("attrList",byAttrValueId);
        map.put("attrValueSelectedList",selectedAttrValueList);
        map.put("urlParam",geturlParam(searchParam,""));
        map.put("catalog3Id",searchParam.getCatalog3Id() == null ? "" : searchParam.getCatalog3Id());
        return "list";
    }

    private String geturlParam(SearchParam searchParam,String valueId){
        String url = "";
        String keyword = searchParam.getKeyword();
        String catalog3Id = searchParam.getCatalog3Id();
        String[] attrValue = searchParam.getValueId();
        if(StringUtils.isNotBlank(keyword)){
            url += "keyword=" + keyword;
        }
        if(StringUtils.isNotBlank(catalog3Id)){
            if(StringUtils.isNotBlank(url)){
                url += "&";
            }
            url += "catalog3Id=" + catalog3Id;
        }
        if(attrValue != null){
            for (String s : attrValue) {
                if(!s.equals(valueId)){
                    url += "&valueId=" + s;
                }
            }
        }
        return url;
    }
}
