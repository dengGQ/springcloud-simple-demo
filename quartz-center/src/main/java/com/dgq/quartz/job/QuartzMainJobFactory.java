package com.dgq.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgq.quartz.commons.Exception.BusinessException;
import com.dgq.quartz.entity.TaskExecuteRecord;
import com.dgq.quartz.service.TaskExecuteRecordService;
import com.dgq.quartz.util.ApplicationContextHolder;
import com.dgq.quartz.util.CommonUtil;
import com.dgq.quartz.util.HttpClientUtil;
import com.dgq.quartz.util.ResultEnum;
import com.google.gson.Gson;

/**
 * @ClassName: job
 * @Description: 任务执行入口
 * @author dgq
 * @date 2019年7月24日
 */
@DisallowConcurrentExecution
public class QuartzMainJobFactory implements Job{
	
	private static Logger logger = LoggerFactory.getLogger(QuartzMainJobFactory.class);
	
	public static final Gson GSON = new Gson();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(this);
		TaskExecuteRecordService recordService = (TaskExecuteRecordService) ApplicationContextHolder.getBean("taskExecuteRecordServiceImpl");
		JobDataMap map = context.getMergedJobDataMap();
		String taskNo = String.valueOf(map.get("taskNo")),
				executor = String.valueOf(map.get("executor")), 
				taskName = String.valueOf(map.get("taskName")), 
				url = String.valueOf(map.get("url")), 
				cronExpress = String.valueOf(map.get("cronExpression")), 
				executeParams = String.valueOf(map.get("executeParamter")), 
				sendType = String.valueOf(map.get("sendType"));
		logger.info("定时任务被执行，taskNo:{}, 执行方：{} , 任务名称:{}, url:{}, 触发规则：{}, 执行参数:{}, sendtype:{}", 
				taskNo, executor, taskName, url, cronExpress, executeParams, sendType);
		String taskId = String.join("-", taskNo, executor);
		TaskExecuteRecord record = new TaskExecuteRecord(taskId, taskName, cronExpress);
		try {
			if(ResultEnum.HTTP.getMessage().equals(sendType)) {
				//发送任务请求
				String result = HttpClientUtil.doPost(url, "application/json", executeParams);
                logger.info("taskNo:{}, executor:{}, 执行结果:{}", taskNo, executor, result);
				//....请求成功
				record.setExecuteStatus(0);
			}else {
				record.setExecuteStatus(1);
				record.setFailReason("不支持的请求类型");
				logger.info("不支持的请求类型：{}", sendType);
			}
		} catch (Exception e) {
			record.setExecuteStatus(2);
			record.setFailReason(CommonUtil.getExceptionDetail(e));
		} finally {
			//执行记录保存
			try {
				recordService.addTaskExecuteRecord(record);
			} catch (BusinessException e) {
				e.printStackTrace();
				logger.info("保存执行记录失败, taskNo:{},执行方:{},任务名称:{},url:{},触发规则：{},执行参数:{}, sendtype:{}", 
						taskId, executor, taskName, url, cronExpress, executeParams, sendType);
			}
		}
	}
}
