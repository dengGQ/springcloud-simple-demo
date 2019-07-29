package com.dgq.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = {"com.dgq.quartz.mapper"})
@SpringBootApplication
public class QuartzCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzCenterApplication.class, args);
	}

}
