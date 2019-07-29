package com.dgq.quartz.service;

import org.quartz.SchedulerException;

import com.dgq.quartz.entity.QuartzTaskInfo;

public interface QuartzService {
	
	/**
     * 新增一个job
     * @param taskInfo
     * @return
     * @throws ClassNotFoundException
     * @throws SchedulerException
     */
	String addTask(QuartzTaskInfo taskInfo) throws ClassNotFoundException, SchedulerException;

	/**
     * 修改task
     * @param triggerName
     * @param cron
     * @return
     */
	String editTask(QuartzTaskInfo taskInfo) throws Exception;
	
	/**
     * 删除任务
     * @param taskInfo
     * @throws Exception
     */
	String delTask(QuartzTaskInfo taskInfo) throws Exception;
	
	/** 
     * 恢复任务 
     * @param taskInfo
     */
	String resumeTask(QuartzTaskInfo taskInfo);
	
	/**
	 * 暂停定时任务
	 * @param @param taskInfo
	 */
	String pauseTask(QuartzTaskInfo taskInfo);
	
	/**
	 * 暂停调度器
	 */
	String shutdown();
	
	/**
	 * 启动调度器
	 */
	String start();
}

