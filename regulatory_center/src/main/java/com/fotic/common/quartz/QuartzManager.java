package com.fotic.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
* @Description: 定时器管理
* @author dgq 
* @date 2018年4月25日
*/
public class QuartzManager{
	
	private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);
	
	private Scheduler clusterScheduler;//项目启动时注入  
	
    public void init() throws SchedulerException{
    	/*JobKey jobKey = JobKey.jobKey("sms_job", "sms_job_group");
        JobDetail jobDetail = clusterScheduler.getJobDetail(jobKey);//xml中配置了 
   
        List<? extends Trigger> tigge1 = clusterScheduler.getTriggersOfJob(jobKey);
        System.out.println(tigge1.size());
        Trigger trigger3 = tigge1.get(0);
        
        TriggerKey triggerKey = TriggerKey.triggerKey("sms_trigger", "sms_trigger_group");
        Trigger trigger = clusterScheduler.getTrigger(triggerKey);
        
        TriggerState triggerState2 = clusterScheduler.getTriggerState(triggerKey);
        
        Set<String> pausedTriggerGroups = clusterScheduler.getPausedTriggerGroups();
        
        
        clusterScheduler.scheduleJob(trigger);*/
    	
    	 /*TriggerKey triggerKey = TriggerKey.triggerKey("sms_trigger", "sms_trigger_group");
    	clusterScheduler.getListenerManager().addTriggerListener(new MyTriggerListeners(), KeyMatcher.keyEquals(triggerKey));*/
    	try {
			//0 55 17 * * ?
    		this.addJob("push_overduedata", "com.fotic.management.sms.job.PushOverdueDataJob", "0/5 * * ? * *");
    		this.addJob("sms_send", "com.fotic.sms.job.SmsSendJob", "0/5 * * ? * *");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 启动定时器
     */
    public boolean start(){
    	try {
			clusterScheduler.start();
			return true;
		} catch ( SchedulerException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * 关闭调度器
     * @return
     */
    public boolean shutdown(){
    	try {
			clusterScheduler.shutdown(true);
			return true;
		} catch (Exception e) {
			logger.info("定时器停止失败................");
			e.printStackTrace();
			return false;
		}
    }
    
    public void setClusterScheduler(Scheduler clusterScheduler) {  
        this.clusterScheduler = clusterScheduler;  
    }
    
    /**
     * 新增一个job
     * @param jobName job名称
     * @param jobClass  job类，该类必须继承: org.quartz.job
     * @param cronExpression "0/5 * * ? * *"
     * @throws ClassNotFoundException 
     * @throws SchedulerException 
     */
    public void addJob(String jobName, String jobClassPath, String cronExpression) throws ClassNotFoundException, SchedulerException{
        	
		JobKey jobKey = new JobKey(jobName+"_job", jobName+"_group");
    	
    	JobDetail jobDetail = clusterScheduler.getJobDetail(jobKey);
    	if(!clusterScheduler.checkExists(jobKey)){
    		@SuppressWarnings("unchecked")
			Class<? extends Job> targetJob = (Class<? extends Job>) Class.forName(jobClassPath);
    		jobDetail = JobBuilder
    				.newJob(targetJob)
    				.withIdentity(jobKey)
    				.build();
    		Trigger trigger = TriggerBuilder.newTrigger()
    				.withIdentity(jobName+"_trigger", jobName+"_trigger_group")
    				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
    				.build();
    		
    		clusterScheduler.scheduleJob(jobDetail, trigger);
    	}
		
    }
    
    /** 
     * 暂停定时任务 
     * @param allPushMessage 
     * @throws Exception 
     */  
    public void pauseJob(String jobName) throws Exception {  
        JobKey jobKey = JobKey.jobKey(jobName+"_job", jobName+"_group");  
        try {
            clusterScheduler.pauseJob(jobKey); 
        } catch (SchedulerException e) {  
        	logger.info("暂停定时任务失败"+e);  
            throw new Exception("暂停定时任务失败");  
        }  
    }  
  
    /** 
     * 恢复任务 
     * @param 
     * @param 
     * @param 
     * @throws Exception 
     */  
    public void resumeJob(String jobName) throws Exception {
  
        JobKey jobKey = JobKey.jobKey(jobName+"_job", jobName+"_group");  
        try {
        	clusterScheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {  
        	logger.info("恢复定时任务失败"+e);  
            throw new Exception("恢复定时任务失败");  
        }  
    }
    
    /**
     * 删除任务
     * @param jobName
     * @throws Exception
     */
    public void deleteJob(String jobName) throws Exception {
    	JobKey jobKey = JobKey.jobKey(jobName+"_job", jobName+"_group"); 
        try {
        	clusterScheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
        	logger.info("删除定时任务失败"+e);
            throw new Exception("删除定时任务失败");  
        }
    }
    
    /**
     * 修改一个触发器的触发规则cron Expression
     * @param triggerName
     * @param cron
     * @return
     */
    public boolean updateTrigger(String triggerName, String cron){
    	try {
    		CronTrigger oldtrigger = (CronTrigger) clusterScheduler.getTrigger(TriggerKey.triggerKey(triggerName+"_trigger", triggerName+"_trigger_group"));
			TriggerBuilder<CronTrigger> tb = oldtrigger.getTriggerBuilder();
    		Trigger newTrigger = tb.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
    		
    		clusterScheduler.rescheduleJob(oldtrigger.getKey(), newTrigger);
			return true;
		} catch (Exception e) {
			logger.info("修改定触发器失败................");
			e.printStackTrace();
			return false;
		}
    }
}
