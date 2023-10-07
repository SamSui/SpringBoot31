package com.example.cachedemo.service;

import com.example.cachedemo.config.ApplicationCacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService{

    @ApplicationCacheable(value = "data_cache", keyGenerator = "dataKeyGenerator")
    @Override
    public String getData() {
        System.out.println("come into data impl");
        return "data_impl";
    }

    @ApplicationCacheable(value = "user_cache", keyGenerator = "userKeyGenerator")
    @Override
    public String getUser() {
        System.out.println("come into user impl");
        return "user_impl";
    }
}
