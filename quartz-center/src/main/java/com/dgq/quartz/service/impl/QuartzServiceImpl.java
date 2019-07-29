package com.dgq.quartz.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dgq.quartz.entity.QuartzTaskInfo;
import com.dgq.quartz.job.QuartzMainJobFactory;
import com.dgq.quartz.mapper.QuartzTaskInfoMapper;
import com.dgq.quartz.service.QuartzService;
import com.dgq.quartz.util.ResultEnum;
import com.dgq.quartz.util.ResultUtil;
import com.google.gson.Gson;

@Service
public class QuartzServiceImpl implements QuartzService {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);
	public static final Gson GSON = new Gson();
	
	@Autowired
	@Qualifier(value = "myScheduler")
	private Scheduler scheduler;
	
	@Autowired
	private QuartzTaskInfoMapper mapper;

    @Override
    public String addTask(QuartzTaskInfo taskInfo) {
        try {
        	
        	mapper.insert(setInitialValue(taskInfo));
        	logger.info("定时任务数据保存成功: {}", taskInfo);
        	
        	JobKey jobKey = JobKey.jobKey(taskInfo.getTaskNo(), taskInfo.getExecutor());
        	//判断cron表达式是否有效
    		if (!CronExpression.isValidExpression(taskInfo.getCronExpression())) {
				return ResultUtil.success(ResultEnum.CRON_INVALID.getCode(), ResultEnum.CRON_INVALID.getMessage());
			}
        	if(scheduler.checkExists(jobKey)){
        		return ResultUtil.success(ResultEnum.TASKNO_EXIST.getCode(), ResultEnum.TASKNO_EXIST.getMessage());
        	}
        	
        	JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        	jobDetail = JobBuilder.newJob(QuartzMainJobFactory.class).storeDurably()
    				.withDescription(taskInfo.getTaskName()).withIdentity(jobKey).build();
        	updateJobData(taskInfo, jobDetail);
        	
    		Trigger trigger = TriggerBuilder.newTrigger()
    				.withDescription(taskInfo.getTaskName())
    				.withIdentity(taskInfo.getTaskNo(), taskInfo.getExecutor())
    				.withSchedule(CronScheduleBuilder.cronSchedule(taskInfo.getCronExpression()))
    				.build();
    		
    		scheduler.scheduleJob(jobDetail, trigger);
    		
    		return ResultUtil.success(taskInfo);
		} catch (Exception e) {
			logger.info("添加定时任务出现异常，参数：{}", taskInfo.toString());
			e.printStackTrace();
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage(), taskInfo);
		}
    }

    @Override
    public String editTask(QuartzTaskInfo taskInfo){
    	try {
    		JobKey jobKey = JobKey.jobKey(taskInfo.getTaskNo(), taskInfo.getExecutor());

    		if (!CronExpression.isValidExpression(taskInfo.getCronExpression())) {
    			return ResultUtil.success(ResultEnum.CRON_INVALID.getCode(), ResultEnum.CRON_INVALID.getMessage());
        	}
    		if (!scheduler.checkExists(jobKey)) {
    			return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
    		}

    		//修改触发器
    		CronTrigger oldtrigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(taskInfo.getTaskNo(), taskInfo.getExecutor()));
    		Trigger newTrigger = oldtrigger.getTriggerBuilder()
    				.withSchedule(CronScheduleBuilder.cronSchedule(taskInfo.getCronExpression())).build();
    		
    		//修改jobData
    		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
    		updateJobData(taskInfo, jobDetail);
    		
    		scheduler.rescheduleJob(oldtrigger.getKey(), newTrigger);
    		
    		return ResultUtil.success();
    	} catch (Exception e) {
			logger.info("修改定时任务失败，参数：{}", taskInfo.toString());
			e.printStackTrace();
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage(), taskInfo);
		}
    }
    
    @Override
    public String pauseTask(QuartzTaskInfo taskInfo) {  
        try {
        	JobKey jobKey = JobKey.jobKey(taskInfo.getTaskNo(), taskInfo.getExecutor());  
        	if(scheduler.checkExists(jobKey)) {
        		
        		scheduler.pauseJob(jobKey);
        		return ResultUtil.success();
        	}
        	return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
        } catch (SchedulerException e) {  
        	logger.info("暂停定时任务出现异常，参数：{}", taskInfo.toString());  
            e.printStackTrace();
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage(), taskInfo);
        }  
    }  
  
    @Override
    public String resumeTask(QuartzTaskInfo taskInfo){
        try {
        	JobKey jobKey = JobKey.jobKey(taskInfo.getTaskNo(), taskInfo.getExecutor()); 
    		if(scheduler.checkExists(jobKey)) {
    			
    			scheduler.resumeJob(jobKey);
    			
    			return ResultUtil.success();
    		}
    		
    		return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
        } catch (SchedulerException e) {  
        	logger.info("恢复定时任务失败, 参数：{}", taskInfo);
        	return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage(), taskInfo);
        }  
    }
    
    @Override
    public String delTask(QuartzTaskInfo taskInfo) throws Exception {
    	try {
    		JobKey jobKey = JobKey.jobKey(taskInfo.getTaskNo(), taskInfo.getExecutor()); 
    		if(scheduler.checkExists(jobKey)) {
    			
    			scheduler.deleteJob(jobKey);
    			
    			return ResultUtil.success();
    		}
    		
    		return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
        } catch (SchedulerException e) {
        	logger.info("删除定时任务失败, 参数：{}", taskInfo.toString());
        	e.printStackTrace();
            return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage(), taskInfo);
        }
    }
    
    @Override
    public String start(){
    	try {
    		scheduler.start();
    		return ResultUtil.success();
		} catch ( SchedulerException e) {
			logger.info("定时器启动失败................");
			e.printStackTrace();
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage());
		}
    }
    
	@Override
    public String shutdown(){
    	try {
    		scheduler.shutdown(true);
			return ResultUtil.success();
		} catch (Exception e) {
			logger.info("定时器停止失败................");
			e.printStackTrace();
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), e.getMessage());
		}
    }
    
    @SuppressWarnings("unchecked")
	private Map<String, Object> assembleJobData(QuartzTaskInfo taskInfo) {
    	return GSON.fromJson(GSON.toJson(taskInfo), HashMap.class);
	}
    private void updateJobData(QuartzTaskInfo taskInfo, JobDetail jobDetail) {
    	JobDataMap jobDataMap = jobDetail.getJobDataMap();
    	jobDataMap.putAll(assembleJobData(taskInfo));
	}
    private QuartzTaskInfo setInitialValue(QuartzTaskInfo taskInfo) {
    	taskInfo.setCreateTime(LocalDateTime.now());
    	return taskInfo;
	}
}
