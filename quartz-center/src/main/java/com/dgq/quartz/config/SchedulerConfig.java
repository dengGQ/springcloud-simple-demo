package com.dgq.quartz.config;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {
	
	public static final String QUARTZ_PROPERTIES_PATH = "/quartz.yml";
	
	@Autowired
	private DataSource dataSource;
	
	@Bean(name = "mySchedulerFactory")
	public SchedulerFactoryBean mySchedulerFactory() throws Exception{
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setConfigLocation(new ClassPathResource(QUARTZ_PROPERTIES_PATH));
		schedulerFactoryBean.setDataSource(this.dataSource);
		return schedulerFactoryBean;
	}
	
	@Bean(name = "myScheduler")
	public Scheduler myScheduler() throws Exception{
		return mySchedulerFactory().getScheduler();
	}
}
