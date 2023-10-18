package com.example.cachedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.example.cachedemo")
@ServletComponentScan
public class CachedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachedemoApplication.class, args);
	}

}
