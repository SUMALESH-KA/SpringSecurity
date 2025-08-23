package com.sumal;

import jakarta.servlet.ServletContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.ServletContextAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SpringSecurityApplication implements ServletContextAware {

	private static final Logger log = LoggerFactory.getLogger(SpringSecurityApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.getFilterRegistrations()
				.forEach((name, reg) -> log.info("{} -> {}", name, reg.getClassName()));
	}
}
