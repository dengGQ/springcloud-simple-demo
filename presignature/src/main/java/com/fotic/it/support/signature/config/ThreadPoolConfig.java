package com.fotic.it.support.signature.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: mfh
 * @Date: 2019-06-13 11:13
 **/
@Configuration
public class ThreadPoolConfig {
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        /* 设置线程池核心容量 */
        executor.setCorePoolSize(30);
        /* 设置线程池最大容量 */
        executor.setMaxPoolSize(100);
        /* 设置任务队列长度 */
        executor.setQueueCapacity(150);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        /* 设置线程名称前缀 */
        executor.setThreadNamePrefix("signature-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
