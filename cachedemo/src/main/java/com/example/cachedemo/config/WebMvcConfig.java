package com.example.cachedemo.config;

import com.example.cachedemo.filter.TrackingIdFilter;
import com.example.cachedemo.resolver.DataCommandResolver;
import com.example.cachedemo.resolver.UserCommandResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Bean
//    public FilterRegistrationBean<TrackingIdFilter> characterEncodingFilterRegister() {
//        TrackingIdFilter trackingIdFilter = new TrackingIdFilter();
//        FilterRegistrationBean<TrackingIdFilter> registration = new FilterRegistrationBean();
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        registration.setFilter(trackingIdFilter);
//        registration.addUrlPatterns("/*");
//        return registration;
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // resolver can redirect request params and formdata request body, if param is @requestbody , resolver not work
        resolvers.add(new UserCommandResolver());
        resolvers.add(new DataCommandResolver());
    }
}
