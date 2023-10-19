package com.example.cachedemo.watcher;

import org.springframework.lang.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

public class YmalPropertySourceFactory extends DefaultPropertySourceFactory {
  @Override
  public PropertySource<?> createPropertySource(@Nullable String name,EncodedResource resource) throws IOException {
    String sourceName = name != null ? name : resource.getResource().getURL().getPath();

    if (sourceName != null && (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml"))) {
      return new OriginTrackedMapPropertySource(sourceName, loadYml(resource));
    } else {
      return super.createPropertySource(name, resource);
    }
  }

  private Properties loadYml(EncodedResource resource){
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(resource.getResource());
    factory.afterPropertiesSet();
    return factory.getObject();
  }

}
