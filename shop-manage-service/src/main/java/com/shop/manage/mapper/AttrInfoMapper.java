package com.shop.manage.mapper;

import com.shop.bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AttrInfoMapper extends Mapper<PmsBaseAttrInfo> {

    public List<PmsBaseAttrInfo> selectByAttrValueId(@Param("attrValues") String attrValues);

}
