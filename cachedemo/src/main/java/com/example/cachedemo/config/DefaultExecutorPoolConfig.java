package com.example.cachedemo.config;

import com.example.cachedemo.exception.CacheDemoExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class DefaultExecutorPoolConfig implements AsyncConfigurer {
    @Autowired
    @Lazy
    private CacheDemoExceptionHandler cacheDemoExceptionHandler;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor  taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();

        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return cacheDemoExceptionHandler;
    }
}
