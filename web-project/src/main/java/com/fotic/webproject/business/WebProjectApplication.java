package com.fotic.webproject.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

import com.fotic.webproject.jpadata.repository.BaseRepositoryFactoryBean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories(repositoryFactoryBeanClass=BaseRepositoryFactoryBean.class)
@SpringBootApplication
@EnableSwagger2
public class WebProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}

	//这里可以查当前使用的是什么类型的事务管理器
	@Bean
	public Object lookTransactionManager(PlatformTransactionManager manager){
		System.out.println("---- ---------------"+manager.getClass().getName());
		return null;
	}
}

