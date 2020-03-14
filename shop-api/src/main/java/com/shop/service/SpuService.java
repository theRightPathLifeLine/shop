package com.shop.service;

import com.shop.bean.*;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> getSpuList(String catalog3Id);

    List<PmsBaseSaleAttr> getBaseSaleAttrList();

    List<PmsProductInfo> saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> getSpuImageList(String spuId);

    List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId);

    List<PmsProductSaleAttr> getSpuSaleAttrList(String productId, String id);

    List<PmsBaseAttrInfo> getByAttrValueId(String attrValues);
}
