package com.example.cachedemo.service;

import com.example.cachedemo.config.RequestCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheClient {

    @Autowired
    private CacheService cacheService;

    @RequestCacheable
    public String getData(){
        System.out.println("come into data client");
        return "client_" + cacheService.getData();
    }

    @RequestCacheable
    public String getDataWithUser(){
        System.out.println("come into data with user client");
        return "client_" + cacheService.getData() + "_with_" + cacheService.getUser();
    }

    @RequestCacheable
    public String getUser(){
        System.out.println("come into user client");
        return "client_" + cacheService.getUser();
    }
}
