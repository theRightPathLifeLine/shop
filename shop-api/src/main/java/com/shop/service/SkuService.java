package com.shop.service;

import com.shop.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuBySkuId(String skuId);

    List<PmsSkuInfo> getSkuBySpuId(String productId);

    List<PmsSkuInfo> getSkuInfos();
}
