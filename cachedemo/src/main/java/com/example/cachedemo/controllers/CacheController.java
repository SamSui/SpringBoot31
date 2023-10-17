package com.example.cachedemo.controllers;

import com.example.cachedemo.command.DataBody;
import com.example.cachedemo.command.DataCommand;
import com.example.cachedemo.command.UserCommand;
import com.example.cachedemo.service.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/user")
    public String getUser(UserCommand command){
        return command.getUserName()+"_"+command.getUserID();
    }

    @PostMapping("/user2")
    public String getUser2(UserCommand command){
        return command.getUserName()+"_"+command.getUserID();
    }

    @PostMapping("/data1")
    public String getData1(@RequestBody DataCommand command){
        return command.getDataName()+"_"+command.getIpAddress();
    }

    @PostMapping("/data")
    public String getData(@RequestBody DataBody body, DataCommand command){
        System.out.println(body.getDataName()+"_"+body.getIpAddress());
        return command.getDataName()+"_"+command.getIpAddress();
    }
}
