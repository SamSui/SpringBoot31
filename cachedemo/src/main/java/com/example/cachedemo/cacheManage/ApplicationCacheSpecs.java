package com.example.cachedemo.cacheManage;

import java.util.HashMap;
import java.util.Map;

public class ApplicationCacheSpecs {
    private static final String DEFAULT_APP_CACHE_SPEC = "maximumSize=10000,expireAfterWrite=5m";

    private static Map<String,String> SPECS = new HashMap<String, String>();

    static {
        SPECS.put("user_cache", "maximumSize=1000,expireAfterWrite=60s");
        SPECS.put("data_cache", "maximumSize=1000,expireAfterWrite=60s");
    }

    public static String get(String name){
        return SPECS.getOrDefault(name, DEFAULT_APP_CACHE_SPEC);
    }
}
