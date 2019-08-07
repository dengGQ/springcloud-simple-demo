package com.dgq.quartz.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgq.quartz.commons.page.ResultPage;
import com.dgq.quartz.commons.page.ResultPageFactory;
import com.dgq.quartz.commons.service.BaseAbstractServiceImpl;
import com.dgq.quartz.dto.QuartzTaskInfoDTO;
import com.dgq.quartz.entity.QuartzTaskInfo;
import com.dgq.quartz.job.QuartzMainJobFactory;
import com.dgq.quartz.mapper.QuartzTaskInfoMapper;
import com.dgq.quartz.service.QuartzService;
import com.dgq.quartz.util.ResultEnum;
import com.dgq.quartz.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class QuartzServiceImpl extends BaseAbstractServiceImpl<QuartzTaskInfo, QuartzTaskInfoMapper> implements QuartzService {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);
	public static final Gson GSON = new Gson();
	private static final String TRIGGER_TYPE_SIMPLE = "simple";
	@Autowired
	@Qualifier(value = "myScheduler")
	private Scheduler scheduler;
	
	@Autowired
	private QuartzTaskInfoMapper mapper;

	@Override
	public List<QuartzTaskInfo> listQuartzTaskInfos(QuartzTaskInfo taskInfo, int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<QuartzTaskInfo> list = mapper.queryEntityList(taskInfo);
		return list;
	}
	
	@Override
	public ResultPage listForPage(QuartzTaskInfo taskInfo, int pageNum, int pageSize) throws Exception {
		List<QuartzTaskInfo> list = this.listQuartzTaskInfos(taskInfo, pageNum, pageSize);
		return ResultPageFactory.newIntance().build(list);
	}
	
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addTask(QuartzTaskInfo taskInfo) {
        try {
        	
        	if(TRIGGER_TYPE_SIMPLE.equals(taskInfo.getTriggerType())) {
        		taskInfo.setCronExpression(taskInfo.getStartDate().format(DateTimeFormatter.ofPattern("s m H d M ? yyyy")));
        	}
        	
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
    		
    		mapper.insert(setInitialValue(taskInfo));
        	logger.info("定时任务数据保存成功: {}", taskInfo);
    		
    		return ResultUtil.success(taskInfo);
		} catch (Exception e) {
			logger.info("添加定时任务出现异常：{}，参数：{}", e.getMessage(), taskInfo.toString());
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), taskInfo);
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
    		
    		updateQuartzTaskInfoOfCronExpression(taskInfo);
    		
    		//修改触发器
    		CronTrigger oldtrigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(taskInfo.getTaskNo(), taskInfo.getExecutor()));
    		Trigger newTrigger = oldtrigger.getTriggerBuilder()
    				.withSchedule(CronScheduleBuilder.cronSchedule(taskInfo.getCronExpression())).build();
    		
    		
    		scheduler.rescheduleJob(oldtrigger.getKey(), newTrigger);
    		
    		return ResultUtil.success();
    	} catch (Exception e) {
			logger.info("修改定时任务出现异常：{}，参数：{}", e.getMessage(), taskInfo.toString());
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), taskInfo);
		}
    }
    
    @Override
    public String pauseTask(QuartzTaskInfoDTO dto) {  
        try {
        	JobKey jobKey = JobKey.jobKey(dto.getTaskNo(), dto.getExecutor());  
        	if(scheduler.checkExists(jobKey)) {
        		scheduler.pauseJob(jobKey);
        		
        		QuartzTaskInfo taskInfo = dtoConvertEntity(dto);
        		taskInfo.setFrozenStatus(ResultEnum.UNFROZEN.getMessage());
        		updateQuartzTaskInfoOfFrozenStatus(taskInfo);
        		
        		return ResultUtil.success();
        	}
        	return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
        } catch (SchedulerException e) {  
        	logger.info("暂停定时任务出现异常:{}，参数：{}", e.getMessage(), dto.toString());  
			return ResultUtil.fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), dto);
        }  
    }  
  
    @Override
    public String resumeTask(QuartzTaskInfoDTO dto){
        try {
        	JobKey jobKey = JobKey.jobKey(dto.getTaskNo(), dto.getExecutor()); 
    		if(scheduler.checkExists(jobKey)) {
    			
    			scheduler.resumeJob(jobKey);
    			
    			QuartzTaskInfo taskInfo = dtoConvertEntity(dto);
        		taskInfo.setFrozenStatus(ResultEnum.FROZEN.getMessage());
        		updateQuartzTaskInfoOfFrozenStatus(taskInfo);
    			
    			return ResultUtil.success();
    		}
    		
    		return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
        } catch (SchedulerException e) {  
        	logger.info("恢复定时任务出现异常：{}， 参数：{}", e.getMessage(), dto);
        	return ResultUtil.fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), dto);
        }  
    }
    
    @Override
    public String delTask(QuartzTaskInfoDTO dto) throws Exception {
    	try {
    		delQuartzTaskInfo(dto.getTaskNo(), dto.getExecutor());
    		JobKey jobKey = JobKey.jobKey(dto.getTaskNo(), dto.getExecutor()); 
    		if(scheduler.checkExists(jobKey)) {
    			scheduler.deleteJob(jobKey);
    			return ResultUtil.success();
    		}
    		
    		return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
        } catch (SchedulerException e) {
        	logger.info("删除定时任务出现异常：{},  参数：{}", e.getMessage(), dto.toString());
            return ResultUtil.fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), dto);
        }
    }
    
    @Override
    public String start(){
    	try {
    		scheduler.start();
    		return ResultUtil.success();
		} catch ( SchedulerException e) {
			logger.info("定时器启动失败................");
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
    
	@Override
	public void updateQuartzTaskInfoSelective(QuartzTaskInfo taskInfo) {
		Example example = new Example(QuartzTaskInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("taskNo", taskInfo.getTaskNo());
		criteria.andEqualTo("executor", taskInfo.getExecutor());
		taskInfo.setLastModifyTime(LocalDateTime.now());
		mapper.updateByExampleSelective(taskInfo, example);
	}
	
	@Override
	public void updateQuartzTaskInfoOfCronExpression(QuartzTaskInfo taskInfo) {
		QuartzTaskInfo entity = new QuartzTaskInfo(taskInfo.getTaskNo(), taskInfo.getExecutor());
		entity.setCronExpression(taskInfo.getCronExpression());
		updateQuartzTaskInfoSelective(entity);
	}
	@Override
	public void updateQuartzTaskInfoOfFrozenStatus(QuartzTaskInfo taskInfo) {
		QuartzTaskInfo entity = new QuartzTaskInfo(taskInfo.getTaskNo(), taskInfo.getExecutor());
		entity.setFrozenStatus(taskInfo.getFrozenStatus());
		updateQuartzTaskInfoSelective(entity);
	}
	
	@Override
	public void delQuartzTaskInfo(String taskNo, String executor) {
		Example example = new Example(QuartzTaskInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("taskNo", taskNo);
		criteria.andEqualTo("executor", executor);
		mapper.deleteByExample(example);
	}

	@Override
	public String startupOnce(QuartzTaskInfoDTO dto) throws Exception{
		JobKey jobKey = JobKey.jobKey(dto.getTaskNo(), dto.getExecutor()); 
		if(scheduler.checkExists(jobKey)) {
			scheduler.triggerJob(jobKey);
			return ResultUtil.success();
		}
		return ResultUtil.success(ResultEnum.TASKNO_NO_EXIST.getCode(), ResultEnum.TASKNO_NO_EXIST.getMessage());
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
    	taskInfo.setLastModifyTime(LocalDateTime.now());
    	taskInfo.setFrozenStatus(ResultEnum.FROZEN.getMessage());
    	return taskInfo;
	}
    private QuartzTaskInfo dtoConvertEntity(QuartzTaskInfoDTO dto) {
		QuartzTaskInfo quartzTaskInfo = new QuartzTaskInfo();
		BeanUtils.copyProperties(dto, quartzTaskInfo);
		return quartzTaskInfo;
	}
    
    public static void main(String[] args) {
		
    	System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("s m H d M ? yyyy")));
	}
}
