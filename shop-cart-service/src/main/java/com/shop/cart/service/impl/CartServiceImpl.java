package com.shop.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.shop.bean.OmsCartItem;
import com.shop.cart.mapper.CartMapper;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartMapper cartMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public OmsCartItem getCartItemByMembetIdAndSkuId(String memberId, String skuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        return cartMapper.selectOne(omsCartItem);
    }

    @Override
    public void updateCartItem(OmsCartItem cartItem) {
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo("id",cartItem.getId());
        cartMapper.updateByExampleSelective(cartItem,example);
    }

    @Override
    public void addCartItem(OmsCartItem omsCartItem) {
        cartMapper.insertSelective(omsCartItem);
    }

    @Override
    public void flushCartCache(OmsCartItem omsCartItem) {
        HashOperations<String,String,String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put("omsCartItem:" + omsCartItem.getMemberId(), omsCartItem.getProductSkuId(), JSON.toJSONString(omsCartItem));
    }

    @Override
    public List<OmsCartItem> getFormCache(String memberId) {
        HashOperations<String,String,String> hash = stringRedisTemplate.opsForHash();
        List<String> values = hash.values("omsCartItem:" + memberId);
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        for (String value : values) {
            omsCartItems.add(JSON.parseObject(value,OmsCartItem.class));
        }
        return omsCartItems;
    }

    @Override
    public OmsCartItem getOneFormCache(String memberId,String skuId) {
        HashOperations<String,String,String> hash = stringRedisTemplate.opsForHash();
        String s = hash.get("omsCartItem:" + memberId, skuId);
        return JSON.parseObject(s,OmsCartItem.class);
    }
}
