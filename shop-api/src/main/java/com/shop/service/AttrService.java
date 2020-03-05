package com.shop.service;

import com.shop.bean.PmsBaseAttrInfo;
import com.shop.bean.PmsBaseAttrValue;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> getAttInfo(String catalog3Id);

    List<PmsBaseAttrValue> getAttValue(String attrId);

    void save(PmsBaseAttrInfo pmsBaseAttrInfo);
}
