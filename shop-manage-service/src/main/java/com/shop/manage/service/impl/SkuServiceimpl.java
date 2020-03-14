package com.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.bean.PmsSkuAttrValue;
import com.shop.bean.PmsSkuImage;
import com.shop.bean.PmsSkuInfo;
import com.shop.bean.PmsSkuSaleAttrValue;
import com.shop.manage.mapper.PmsSkuAttrValueMapper;
import com.shop.manage.mapper.PmsSkuImageMapper;
import com.shop.manage.mapper.PmsSkuInfoMapper;
import com.shop.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.shop.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "sku")
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


    @Cacheable(keyGenerator = "myKeyGenerator")
    @Override
    public PmsSkuInfo getSkuBySkuId(String skuId) {
        PmsSkuInfo skuInfo = new PmsSkuInfo();
        skuInfo.setId(skuId);
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectOne(skuInfo);
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        pmsSkuInfo.setPmsSkuImageList(pmsSkuImageMapper.select(pmsSkuImage));
//        PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
//        pmsSkuAttrValue.setSkuId(skuId);
//        pmsSkuInfo.setPmsSkuAttrValueList(pmsSkuAttrValueMapper.select(pmsSkuAttrValue));
//        PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
//        pmsSkuSaleAttrValue.setSkuId(skuId);
//        pmsSkuInfo.setPmsSkuSaleAttrValueList(pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue));
        return pmsSkuInfo;
    }

    @Cacheable(keyGenerator = "myKeyGenerator")
    @Override
    public List<PmsSkuInfo> getSkuBySpuId(String productId) {
        return pmsSkuInfoMapper.selectSkuBySpuId(productId);
    }

    @Override
    public List<PmsSkuInfo> getSkuInfos() {
        List<PmsSkuInfo> skuInfos = pmsSkuInfoMapper.selectAll();
        skuInfos.stream().forEach(p -> {
            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(p.getId());
            p.setPmsSkuAttrValueList(pmsSkuAttrValueMapper.select(pmsSkuAttrValue));
        });
        return skuInfos;
    }
}
