package com.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.bean.PmsSkuImage;
import com.shop.bean.PmsSkuInfo;
import com.shop.manage.mapper.PmsSkuAttrValueMapper;
import com.shop.manage.mapper.PmsSkuImageMapper;
import com.shop.manage.mapper.PmsSkuInfoMapper;
import com.shop.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.shop.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SkuServiceimpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        List<PmsSkuImage> pmsSkuImageList = pmsSkuInfo.getPmsSkuImageList();
        if(StringUtils.isBlank(skuDefaultImg)){
            if(pmsSkuImageList.size() > 0){
                pmsSkuInfo.setSkuDefaultImg(pmsSkuImageList.get(0).getImgUrl());
            }
        }
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        pmsSkuImageList.parallelStream().forEach(p -> {
            p.setSkuId(pmsSkuInfo.getId());
            pmsSkuImageMapper.insertSelective(p);
        });
        pmsSkuInfo.getPmsSkuAttrValueList().parallelStream().forEach(p -> {
            p.setSkuId(pmsSkuInfo.getId());
            pmsSkuAttrValueMapper.insertSelective(p);
        });
        pmsSkuInfo.getPmsSkuSaleAttrValueList().parallelStream().forEach(p -> {
            p.setSkuId(pmsSkuInfo.getId());
            pmsSkuSaleAttrValueMapper.insertSelective(p);
        });
    }
}
