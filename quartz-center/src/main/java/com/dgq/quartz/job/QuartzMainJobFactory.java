package com.dgq.quartz.job;

import java.util.Objects;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgq.quartz.entity.TaskExecuteRecord;
import com.dgq.quartz.service.TaskExecuteRecordService;
import com.dgq.quartz.util.ApplicationContextHolder;
import com.dgq.quartz.util.CommonUtil;
import com.dgq.quartz.util.OkHttpClientUtil;
import com.dgq.quartz.util.OkHttpClientUtil.ResponseResult;

/**
 * @ClassName: job
 * @Description: 任务执行入口
 * @author dgq
 * @date 2019年7月24日
 */
@DisallowConcurrentExecution
public class QuartzMainJobFactory implements Job{
	
	private static Logger logger = LoggerFactory.getLogger(QuartzMainJobFactory.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(Thread.currentThread());
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
		//初始执行记录
		TaskExecuteRecord record = new TaskExecuteRecord(taskId, taskName, cronExpress);
		try {
			JobExecutor jobExecutor = (JobExecutor)ApplicationContextHolder.getBean(sendType);
			if(Objects.isNull(jobExecutor)) {
				record.setExecuteStatus(1);
				record.setFailReason("不支持的请求类型");
				logger.info("不支持的请求类型：{}", sendType);
			}else {
//				String result = jobExecutor.execute(url, executeParams);
				//异步发送http请求
				jobExecutor.submit(url, executeParams, response->{
					requestCallBack(record, taskNo, executor, response, recordService);
				});
			}
		} catch (Exception e) {
			record.setExecuteStatus(2);
			record.setFailReason(CommonUtil.getExceptionDetail(e));
			logger.info("新增执行记录：{}", record);
			recordService.addTaskExecuteRecord(record);
		}
	}
	
	public void requestCallBack(TaskExecuteRecord record, String taskNo, String executor, ResponseResult response, TaskExecuteRecordService recordService) {
		logger.info("taskNo:{}, executor:{}, 执行结果:{}", taskNo, executor, response.getResponseBody());
		if(Objects.equals(OkHttpClientUtil.RequestStatusEnum.SUCCESS, response.getRequestStatus())
				&& Objects.deepEquals(response.getHttpCode(), 200)) {
			record.setExecuteStatus(0);
		}else {
			record.setExecuteStatus(1);
			record.setFailReason(response.getResponseBody());
		}
		logger.info("新增执行记录：{}", record);
		recordService.addTaskExecuteRecord(record);
	}
}
