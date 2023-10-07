package com.example.cachedemo.keygenerator;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("userKeyGenerator")
public class UserKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return method.getName()+"_user";
    }
}
