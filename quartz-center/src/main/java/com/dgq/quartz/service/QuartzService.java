package com.dgq.quartz.service;


import java.util.List;

import org.quartz.SchedulerException;

import com.dgq.quartz.commons.page.ResultPage;
import com.dgq.quartz.commons.service.BaseService;
import com.dgq.quartz.dto.QuartzTaskInfoDTO;
import com.dgq.quartz.entity.QuartzTaskInfo;

public interface QuartzService extends BaseService<QuartzTaskInfo>{
	
	List<QuartzTaskInfo> listQuartzTaskInfos(QuartzTaskInfo taskInfo, int pageNum, int pageSize) throws Exception;
	
	ResultPage listForPage(QuartzTaskInfo taskInfo, int pageNum, int pageSize) throws Exception;
	
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
	String delTask(QuartzTaskInfoDTO dto) throws Exception;
	
	/** 
     * 恢复任务 
     * @param taskInfo
     */
	String resumeTask(QuartzTaskInfoDTO dto);
	
	/**
	 * 暂停定时任务
	 * @param @param taskInfo
	 */
	String pauseTask(QuartzTaskInfoDTO dto);
	
	/**
	 * 暂停调度器
	 */
	String shutdown();
	
	/**
	 * 启动调度器
	 */
	String start();
	
	/**
	 * 根据taskNo和executor修改参数对象中不为空的字段，忽略为空字段
	 * @param @param taskInfo    参数
	 * @return void    返回类型
	 * @throws
	 */
	void updateQuartzTaskInfoSelective(QuartzTaskInfo taskInfo);
	
	/**
	 * @Description: 根据taskNo和executor修改cron表达式
	 * @param @param taskInfo    参数
	 * @return void    返回类型
	 * @throws
	 */
	void updateQuartzTaskInfoOfCronExpression(QuartzTaskInfo taskInfo);
	
	/**
	 * @Description: 根据taskNo和executor修改冻结状态
	 * @param @param taskInfo    参数
	 * @return void    返回类型
	 * @throws
	 */
	void updateQuartzTaskInfoOfFrozenStatus(QuartzTaskInfo taskInfo);
	
	void delQuartzTaskInfo(String taskNo, String executor);
}

