package com.dgq.quartz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dgq.quartz.commons.page.ResultPage;
import com.dgq.quartz.dto.QuartzTaskInfoDTO;
import com.dgq.quartz.dto.RequestParams;
import com.dgq.quartz.entity.QuartzTaskInfo;
import com.dgq.quartz.service.QuartzService;
import com.dgq.quartz.service.TaskExecuteRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "定时器管理")
@RequestMapping("/quartzMgr")
@Controller
public class QuartzController {
	
	public static final Logger logger = LoggerFactory.getLogger(QuartzController.class);
	
	@Autowired
	private QuartzService quartzService;
	
	@Autowired
	private TaskExecuteRecordService taskExecRecordService;
	
	@GetMapping("/jumpPage/{page}")
	public String jumpPage(@PathVariable("page")String page) throws Exception {
		return page;
	}
	
	@ApiOperation(value = "分页查询任务列表")
	@PostMapping("/list")
	@ResponseBody
	public ResultPage list(@RequestBody RequestParams rp) throws Exception {
		ResultPage resultPage = quartzService.queryEntityListForPage(rp.getTaskInfo(), rp.getPageNumber(), rp.getPageSize(), "task_no, executor, create_time desc");
		return resultPage;
	}
	@ApiOperation(value = "任务执行记录列表")
	@PostMapping("/taskExecuteRecordList")
	@ResponseBody
	public ResultPage taskExecuteRecordList(@RequestBody RequestParams rp) throws Exception {
		ResultPage resultPage = taskExecRecordService.queryEntityListForPage(rp.getRecord(), rp.getPageNumber(), rp.getPageSize(), "task_id,last_execute_time desc");
		return resultPage;
	}
	
	@ApiOperation(value = "新增定时任务")
	@PostMapping("/add")
	@ResponseBody
	public String addQuartz(@RequestBody QuartzTaskInfo taskInfo) throws Exception {
		logger.info("新增定时任务，请求参数：{}", taskInfo.toString());
		return quartzService.addTask(taskInfo);
	}
	
	@ApiOperation(value = "修改定时任务")
	@PostMapping("/edit")
	@ResponseBody
	public String editQuartz(@RequestBody QuartzTaskInfo taskInfo) throws Exception {
		logger.info("修改定时任务, 请求参数：{}", taskInfo.toString());
		return quartzService.editTask(taskInfo);
	}
	
	@ApiOperation(value = "删除定时任务")
	@PostMapping("/del")
	@ResponseBody
	public String delQuartz(@RequestBody QuartzTaskInfoDTO dto) throws Exception {
		logger.info("删除定时任务, 请求参数：{}", dto.toString());
		return quartzService.delTask(dto);
	}
	
	@ApiOperation(value = "暂停定时任务")
	@PostMapping("/pause")
	@ResponseBody
	public String pauseJob(@RequestBody QuartzTaskInfoDTO dto) throws Exception {
		logger.info("暂停定时任务, 请求参数：{}", dto.toString());
		return quartzService.pauseTask(dto);
	}
	
	@ApiOperation(value = "恢复定时任务")
	@PostMapping("/resume")
	@ResponseBody
	public String resumeTask(@RequestBody QuartzTaskInfoDTO dto) throws Exception {
		logger.info("恢复定时任务, 请求参数：{}", dto.toString());
		return quartzService.resumeTask(dto);
	}
	
	@ApiOperation(value = "立即运行一次")
	@PostMapping("/executeOnce")
	@ResponseBody
	public String executeOnce(@RequestBody QuartzTaskInfoDTO dto) throws Exception {
		logger.info("立即执行一次, 请求参数：{}", dto.toString());
		return quartzService.startupOnce(dto);
	}
	
	@ApiOperation(value = "启动调度器")
	@PostMapping("/startScheduler")
	@ResponseBody
	public String startScheduler() throws Exception {
		return quartzService.start();
	}
	
	@ApiOperation(value = "关闭调度器")
	@PostMapping("/shutdownScheduler")
	@ResponseBody
	public String shutdownScheduler() throws Exception {
		return quartzService.shutdown();
	}
}
