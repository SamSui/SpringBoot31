package com.example.cachedemo.asyncTask;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

    @Async
    public void testPrintLog(){
        System.out.println("async test print log");
    }

}
