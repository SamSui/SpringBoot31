package com.example.cachedemo.aspect;

import com.example.cachedemo.cacheManage.InvocationContext;
import com.example.cachedemo.cacheManage.RequestCacheManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Aspect
@Component
public class RequestCacheableAspect {

    @Autowired
    private RequestCacheManager cacheManager;

    @Around("@annotation(com.example.cachedemo.config.RequestCacheable)")
    public Object requestCacheHandle(ProceedingJoinPoint joinPoint){
        try {
            InvocationContext context = new InvocationContext(joinPoint.getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs());
            Optional<Object> result =  cacheManager.get(context);
            if(result.isPresent()){
                return result.get();
            }

            Object resultObj = joinPoint.proceed();
            if(resultObj != null){
                cacheManager.put(context, resultObj);
            }
            return resultObj;
        } catch (Throwable e) {
            System.out.println("request cache exception"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
