package com.shop.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.bean.UmsMember;
import com.shop.bean.UmsMemberReceiveAddress;
import com.shop.service.UserService;
import com.shop.user.mapper.UmsMemberReceiveAddressMapper;
import com.shop.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userMapper.selectAll();
        return umsMembers;
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceviceByMemberId(String id) {
        Example e = new Example(UmsMemberReceiveAddress.class);
        e.and().andEqualTo("memberId",id);
        List<UmsMemberReceiveAddress> receiveAddresses1 = umsMemberReceiveAddressMapper.selectByExample(e);
        UmsMemberReceiveAddress u = new UmsMemberReceiveAddress();
        u.setMemberId(id);
        List<UmsMemberReceiveAddress> receiveAddresses = umsMemberReceiveAddressMapper.select(u);
        return receiveAddresses;
    }
}


