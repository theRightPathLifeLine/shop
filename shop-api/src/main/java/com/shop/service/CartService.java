package com.shop.service;

import com.shop.bean.OmsCartItem;

import java.util.List;

public interface CartService {
    OmsCartItem getCartItemByMembetIdAndSkuId(String memberId, String skuId);

    void updateCartItem(OmsCartItem cartItem);

    void addCartItem(OmsCartItem omsCartItem);

    void flushCartCache(OmsCartItem omsCartItem);

    List<OmsCartItem> getFormCache(String memberId);

    OmsCartItem getOneFormCache(String memberId,String skuId);
}
