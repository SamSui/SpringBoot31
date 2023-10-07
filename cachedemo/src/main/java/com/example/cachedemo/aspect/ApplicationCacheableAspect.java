package com.example.cachedemo.aspect;

import com.example.cachedemo.cacheManage.CaffeineCacheManager;
import com.example.cachedemo.config.ApplicationCacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.util.Map;


@Aspect
@Component
public class ApplicationCacheableAspect {

    @Autowired
    private CaffeineCacheManager caffeineCacheManager;

    @Autowired
    @Qualifier("defaultKeyGenerator")
    private KeyGenerator defaultKeyGenerator;

    @Autowired
    private Map<String,KeyGenerator> keyGenerator;

    @Around("@annotation(applicationCacheable)")
    public Object applicationCacheHandle(final ProceedingJoinPoint joinPoint, final ApplicationCacheable applicationCacheable) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if(!(signature instanceof MethodSignature)){
            return joinPoint.proceed();
        }

        Cache cache = caffeineCacheManager.getCache(applicationCacheable.value());
        if(cache == null){
            return joinPoint.proceed();
        }

        KeyGenerator cacheKeyGenerator = keyGenerator.getOrDefault(applicationCacheable.keyGenerator(), defaultKeyGenerator);
        Object cacheKey = cacheKeyGenerator.generate(joinPoint.getTarget(), ((MethodSignature) signature).getMethod(), joinPoint.getArgs());
        Cache.ValueWrapper result = cache.get(cacheKey);

        if(result == null){
            Object resultObject = joinPoint.proceed();
            if(resultObject != null){
                cache.put(cacheKey, resultObject);
            }

            return resultObject;
        }

        return result.get();
    }
}
