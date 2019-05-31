package com.dgq.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
//@RefreshScope //开启配置信息更新机制，需在pom中加入@(spring-boot-starter-actuator),调用/actuator/refresh来执行更新
public class UserManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagerApplication.class, args);
	}
	
	@Value("${environment}")
	private String environment;
	
	@Value("${server.port}")
	private Integer port;
	
	@RequestMapping("/getEnvironment")
	public String getEnvironment() {
		return environment+"-"+port;
	}
}
