package com.example.cachedemo.cacheManage;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CaffeineCacheManager implements CacheManager {
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>(16);

    @Override
    public Cache getCache(String name) {
        return this.cacheMap.computeIfAbsent(name, this::createCaffeineCache);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    protected Cache createCaffeineCache(String name) {
        return new CaffeineCache(name, createNativeCaffeineCache(name), false);
    }

    protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache(String name) {
        String cacheSpecification = ApplicationCacheSpecs.get(name);
        return Caffeine.from(cacheSpecification).build();
    }
}
