package com.example.cachedemo.cacheManage;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "request")
public class RequestCacheManager {
    private final Map<InvocationContext, Object> cache = new ConcurrentHashMap<>();

    private final AtomicInteger cacheHit = new AtomicInteger(0);
    private final AtomicInteger cacheLookUp = new AtomicInteger(0);

    public Optional<Object> get(InvocationContext invocationContext) {

        this.cacheLookUp.incrementAndGet();

        Optional<Object> result;
        Object obj = cache.get(invocationContext);
        if (obj != null) {
            this.cacheHit.incrementAndGet();
            result = Optional.of(obj);
            System.out.println(String.format("[REQUEST CACHE] hit (%s/%s). Key %s.%s", this.cacheHit, this.cacheLookUp,
                    invocationContext.getTargetClass().getCanonicalName(), invocationContext.getTargetMethod()));
        } else {
            result = Optional.empty();

            System.out.println(String.format("[REQUEST CACHE] miss (%s/%s). Key %s.%s", this.cacheHit, this.cacheLookUp,
                    invocationContext.getTargetClass().getCanonicalName(), invocationContext.getTargetMethod()));
        }

        return result;
    }

    public void put(InvocationContext methodInvocation, Object result) {
        if (result != null) {
            this.cache.put(methodInvocation, result);
        }
    }

    public void removeByClass(Class<?> clazz) {
        Set<InvocationContext> keySet = this.cache.keySet();
        for (InvocationContext invocationContext : keySet) {
            if (invocationContext.getTargetClass().equals(clazz)) {
                this.cache.remove(invocationContext);
            }
        }
        System.out.println(String.format("[REQUEST CACHE] removed cached data for class %s", clazz.getCanonicalName()));
    }

    public void removeAll() {
        Set<InvocationContext> keySet = this.cache.keySet();
        keySet.clear();
        System.out.println(String.format("[REQUEST CACHE] removed all in cache"));
    }
}
