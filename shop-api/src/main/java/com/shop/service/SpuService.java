package com.shop.service;

import com.shop.bean.PmsBaseSaleAttr;
import com.shop.bean.PmsProductImage;
import com.shop.bean.PmsProductInfo;
import com.shop.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> getSpuList(String catalog3Id);

    List<PmsBaseSaleAttr> getBaseSaleAttrList();

    List<PmsProductInfo> saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> getSpuImageList(String spuId);

    List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId);
}
