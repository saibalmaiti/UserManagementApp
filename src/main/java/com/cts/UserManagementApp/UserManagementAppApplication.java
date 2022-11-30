package com.cts.UserManagementApp;

import com.cts.UserManagementApp.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementAppApplication {
	@Autowired
	JwtFilter jwtFilter;
	@Bean
	public FilterRegistrationBean jwtFilterBean() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(jwtFilter);
		frb.addUrlPatterns("/api/v1/user/*");
		return frb;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserManagementAppApplication.class, args);
	}

}
