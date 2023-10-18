package com.example.cachedemo.service;

import com.example.cachedemo.asyncTask.AsyncTask;
import com.example.cachedemo.config.ApplicationCacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService{

    @Autowired
    private AsyncTask asyncTask;

    @ApplicationCacheable(value = "data_cache", keyGenerator = "dataKeyGenerator")
    @Override
    public String getData() {
        asyncTask.testPrintLog();
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
