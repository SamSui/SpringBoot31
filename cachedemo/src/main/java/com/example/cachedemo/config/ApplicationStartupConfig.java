package com.example.cachedemo.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ApplicationStartupConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void onAppReady(ApplicationReadyEvent event){
        System.out.println("application ready, please check logic");
    }
}
