package com.shop.manage.configuration;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

@Slf4j
public class MyKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        sb.append("&");
        for (Object obj : params) {
            if (obj != null){
                sb.append(obj.getClass().getName());
                sb.append("&");
                sb.append(JSON.toJSONString(obj));
                sb.append("&");
            }
        }
        String key = DigestUtils.sha256Hex(sb.toString());
        log.info("redis cache key sha256Hex: "+ key);
        return key;
    }
}
