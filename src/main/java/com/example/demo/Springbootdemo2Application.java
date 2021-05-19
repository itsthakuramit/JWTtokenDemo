package com.example.demo;

import javax.servlet.FilterRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.demo.config.JWTFilter;

@SpringBootApplication
public class Springbootdemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springbootdemo2Application.class, args);
	}

	@Bean
	public FilterRegistrationBean<?> filterRegistration() {
		
		FilterRegistrationBean registrationBean = new FilterRegistrationBean<>();
		
		registrationBean.addUrlPatterns("/api/v1/user/*");
		registrationBean.setFilter(new JWTFilter());
		return registrationBean;
		
	}
}
