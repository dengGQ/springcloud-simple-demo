package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.config.CommonsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;


/**
 * @Author: mfh
 * @Date: 2019-06-14 9:47
 **/
@Service
public class SignatureScheduled implements ApplicationRunner {
    @Resource
    private ThreadPoolTaskScheduler taskExecutor;
    private ScheduledFuture<?> future;
    @Resource
    private SignatureScheduledTask signatureScheduledTask;
    @Resource
    private CommonsConfig commonsConfig;

    private static final Logger logger = LoggerFactory.getLogger(SignatureScheduled.class);

    public boolean startTask() {
        boolean flag = false;
        String cron = commonsConfig.getScheduledTaskCycle();

        if (this.future != null && !this.future.isCancelled()) {
            logger.info("签章定时任务已启动，不能重复启动");
        } else {
            future = taskExecutor.schedule(signatureScheduledTask, new CronTrigger(cron));
            if (future != null) {
                flag = true;
                logger.info("签章定时任务启动成功");
            } else {
                logger.info("签章定时任务启动失败");
            }
        }
        return flag;
    }
    /**
     * 停止定时任务
     * @return 是否停止成功
     */
    public boolean stopTask() {
        boolean flag = false;
        if (future != null) {
            boolean cancel = future.cancel(true);
            if (cancel){
                flag = true;
                logger.info("停止签章定时任务成功");
            }else {
                logger.info("停止签章定时任务失败");
            }
        }else {
            flag = true;
            logger.info("签章任务已经停止");
        }
        return flag;
    }

    @Override
    public void run(ApplicationArguments args) {
        this.startTask();
    }
}
