package com.example.cachedemo.config;

import com.example.cachedemo.resolver.DataCommandResolver;
import com.example.cachedemo.resolver.UserCommandResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // resolver can redirect request params and formdata request body, if param is @requestbody , resolver not work
        resolvers.add(new UserCommandResolver());
        resolvers.add(new DataCommandResolver());
    }
}
