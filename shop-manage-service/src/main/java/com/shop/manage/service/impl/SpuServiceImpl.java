package com.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.bean.*;
import com.shop.manage.mapper.*;
import com.shop.service.SpuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@CacheConfig(cacheNames = "spu")
@Service
public class SpuServiceImpl implements SpuService{

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;

    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Autowired
    PmsProductImageMapper pmsProductImageMapper;

    @Autowired
    AttrInfoMapper attrInfoMapper;

    @Override
    public List<PmsProductInfo> getSpuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return productInfoMapper.select(pmsProductInfo);
    }

    @Override
    public List<PmsBaseSaleAttr> getBaseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public List<PmsProductInfo> saveSpuInfo(PmsProductInfo pmsProductInfo) {
        String id = pmsProductInfo.getId();
        if(StringUtils.isBlank(id)){
            pmsProductInfoMapper.insertSelective(pmsProductInfo);
            List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductInfo.getPmsProductSaleAttrList();
            pmsProductSaleAttrList.stream().forEach(p ->{
                p.setProductId(pmsProductInfo.getId());
                pmsProductSaleAttrMapper.insertSelective(p);
                List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList = p.getPmsProductSaleAttrValueList();
                pmsProductSaleAttrValueList.stream().forEach(p1 ->{
                    p1.setProductId(pmsProductInfo.getId());
                    pmsProductSaleAttrValueMapper.insertSelective(p1);
                });
            });
            pmsProductInfo.getPmsProductImageList().stream().forEach(p -> {
                p.setProductId(pmsProductInfo.getId());
                pmsProductImageMapper.insertSelective(p);
            });
        }else{
            Example e = new Example(PmsProductInfo.class);
            e.createCriteria().andEqualTo("id",id);
            pmsProductInfoMapper.updateByExampleSelective(pmsProductInfo,e);

            PmsProductImage pmsProductImage = new PmsProductImage();
            pmsProductImage.setProductId(id);
            pmsProductImageMapper.delete(pmsProductImage);

            PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
            pmsProductSaleAttr.setProductId(id);
            pmsProductSaleAttrMapper.delete(pmsProductSaleAttr);

            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setProductId(id);
            pmsProductSaleAttrValueMapper.delete(pmsProductSaleAttrValue);


            pmsProductInfo.getPmsProductSaleAttrList().stream().forEach(p ->{
                p.setProductId(id);
                pmsProductSaleAttrMapper.insertSelective(p);
                List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList = p.getPmsProductSaleAttrValueList();
                pmsProductSaleAttrValueList.stream().forEach(p1 ->{
                    p1.setProductId(id);
                    pmsProductSaleAttrValueMapper.insertSelective(p1);
                });
            });
            pmsProductInfo.getPmsProductImageList().stream().forEach(p -> {
                p.setProductId(id);
                pmsProductImageMapper.insertSelective(p);
            });

        }

        return null;
    }

    @Override
    public List<PmsProductImage> getSpuImageList(String spuId) {
        PmsProductImage pmsProductImage = new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        return pmsProductImageMapper.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);
        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs){
//            Example e = new Example(PmsProductSaleAttrValue.class);
//            e.createCriteria().andEqualTo("",pmsProductSaleAttr.getSaleAttrId());
            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            pmsProductSaleAttrValue.setProductId(spuId);
            productSaleAttr.setPmsProductSaleAttrValueList(pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue));
        }

        return pmsProductSaleAttrs;
    }


    @Cacheable(keyGenerator = "myKeyGenerator")
    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrList(String productId, String id) {
        return pmsProductSaleAttrMapper.selectBySpuIdAndSkuId(productId,id);
    }

    public List<PmsBaseAttrInfo> getByAttrValueId(String attrValues){
        return attrInfoMapper.selectByAttrValueId(attrValues);
    }
}
