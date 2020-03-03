package com.shop.service;


import com.shop.bean.UmsMember;
import com.shop.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceviceByMemberId(String id);
}
