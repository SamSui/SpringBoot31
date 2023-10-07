package com.example.cachedemo.controllers;

import com.example.cachedemo.service.CacheClient;
import com.example.cachedemo.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheClient cacheClient;

    @GetMapping("/testUser")
    public String getTestUser(){
        return cacheClient.getUser();
    }

    @GetMapping("/testData")
    public String getTestData(){
        return cacheClient.getData();
    }

    @GetMapping("/testDataWithUser")
    public String getTestDataWithUser(){
        return cacheClient.getDataWithUser();
    }

    @GetMapping("/testDataWithUser2")
    public String getTestDataWithUser2(){
        return cacheClient.getData()+"_" + cacheClient.getUser()+"_"+cacheClient.getData();
    }
}
