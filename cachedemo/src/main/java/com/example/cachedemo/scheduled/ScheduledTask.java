package com.example.cachedemo.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Scheduled(fixedDelay = 2000)
    public void printLog(){
        System.out.println("scheduled task executor");
    }
}
