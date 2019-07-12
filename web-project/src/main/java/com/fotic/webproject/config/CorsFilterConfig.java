package com.fotic.webproject.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig{
	
	@Bean
	public FilterRegistrationBean<Filter> corsFilter(){
		System.out.println("--------------------------------------");
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("*");
		corsConfig.addAllowedHeader("*");
		corsConfig.addAllowedMethod("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/cors/**", corsConfig);
		
		CorsFilter corsFilter = new CorsFilter(source);
		bean.setFilter(corsFilter);
		bean.setOrder(1);
		return bean;
	}
}
