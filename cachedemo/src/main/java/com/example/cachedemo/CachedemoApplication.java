package com.example.cachedemo;

import com.example.cachedemo.watcher.YmalPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = "com.example.cachedemo")
@ServletComponentScan(basePackages = "com.example.cachedemo")
@PropertySources({
		@PropertySource("file:/Users/suixiangxi/temp/watcher/bootstrap.properties"),
		@PropertySource(value = "file:/Users/suixiangxi/temp/watcher/app.yml", factory = YmalPropertySourceFactory.class)
})
@EnableConfigurationProperties
public class CachedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachedemoApplication.class, args);
	}
}
