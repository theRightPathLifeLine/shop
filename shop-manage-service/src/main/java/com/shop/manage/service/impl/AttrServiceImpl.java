package com.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.bean.PmsBaseAttrInfo;
import com.shop.bean.PmsBaseAttrValue;
import com.shop.manage.mapper.AttrInfoMapper;
import com.shop.manage.mapper.AttrValueMapper;
import com.shop.manage.mapper.PmsBaseAttrValueMapper;
import com.shop.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    AttrInfoMapper attrInfoMapper;

    @Autowired
    AttrValueMapper attrValueMapper;

    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> getAttInfo(String catalog3Id) {
        Example example = new Example(PmsBaseAttrInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrInfoMapper.selectByExample(example);
        pmsBaseAttrInfos.parallelStream().forEach(p -> {
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(p.getId());
            p.setAttrValueList(pmsBaseAttrValueMapper.select(pmsBaseAttrValue));
        });
        return pmsBaseAttrInfos;
    }

    @Override
    public List<PmsBaseAttrValue> getAttValue(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        return attrValueMapper.select(pmsBaseAttrValue);
    }

    @Override
    public void save(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        if(StringUtils.isBlank(id)){
            attrInfoMapper.insertSelective(pmsBaseAttrInfo);
            pmsBaseAttrInfo.getAttrValueList().stream().forEach(p -> {
                p.setAttrId(pmsBaseAttrInfo.getId());
                attrValueMapper.insertSelective(p);
            });
        }else{
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",id);
            attrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
            pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
            attrValueMapper.delete(pmsBaseAttrValueDel);

            // 删除后，将新的属性值插入
            pmsBaseAttrInfo.getAttrValueList().stream().forEach(attrValueMapper::insertSelective);
        }

    }
}
