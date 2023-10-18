package com.example.cachedemo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// disable default error filter
		setRegisterErrorPageFilter(false);
		return application.sources(CachedemoApplication.class);
	}

}
