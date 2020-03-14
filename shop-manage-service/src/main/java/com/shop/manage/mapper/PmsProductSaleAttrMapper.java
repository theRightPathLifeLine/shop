package com.shop.manage.mapper;

import com.shop.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr>{
    List<PmsProductSaleAttr> selectBySpuIdAndSkuId(@Param("productId") String productId,@Param("id") String id);
}

