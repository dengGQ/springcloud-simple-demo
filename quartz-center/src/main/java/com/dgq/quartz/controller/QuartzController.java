package com.dgq.quartz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dgq.quartz.commons.page.ResultPage;
import com.dgq.quartz.entity.QuartzTaskInfo;
import com.dgq.quartz.service.QuartzService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "定时器管理")
@RequestMapping("/quartzMgr")
@RestController
public class QuartzController {
	
	public static final Logger logger = LoggerFactory.getLogger(QuartzController.class);
	
	@Autowired
	private QuartzService quartzService;
	
	
	@ApiOperation(value = "分页查询任务列表")
	@PostMapping("/list")
	public ResultPage listForPage(@RequestBody QuartzTaskInfo taskInfo, 
			@RequestParam(defaultValue = "1")int pageNum,
			@RequestParam(defaultValue = "30")int pageSize) throws Exception {
	
		ResultPage resultPage = quartzService.listForPage(taskInfo, pageNum, pageSize);
		return resultPage;
	}
	
	@ApiOperation(value = "新增定时任务")
	@PostMapping("/add")
	public String addQuartz(@RequestBody QuartzTaskInfo taskInfo) throws Exception {
		logger.info("新增定时任务，请求参数：{}", taskInfo.toString());
		return quartzService.addTask(taskInfo);
	}
	
	@ApiOperation(value = "修改定时任务")
	@PostMapping("/edit")
	public String editQuartz(QuartzTaskInfo taskInfo) throws Exception {
		logger.info("修改定时任务, 请求参数：{}", taskInfo.toString());
		return quartzService.editTask(taskInfo);
	}
	
	@ApiOperation(value = "删除定时任务")
	@PostMapping("/del")
	public String delQuartz(QuartzTaskInfo taskInfo) throws Exception {
		logger.info("修改定时任务, 请求参数：{}", taskInfo.toString());
		return quartzService.delTask(taskInfo);
	}
	
	@ApiOperation(value = "暂停定时任务")
	@PostMapping("/pause")
	public String pauseJob(QuartzTaskInfo taskInfo) throws Exception {
		logger.info("暂停定时任务, 请求参数：{}", taskInfo.toString());
		return quartzService.pauseTask(taskInfo);
	}
	
	@ApiOperation(value = "恢复定时任务")
	@PostMapping("/resume")
	public String resumeTask(QuartzTaskInfo taskInfo) throws Exception {
		logger.info("恢复定时任务, 请求参数：{}", taskInfo.toString());
		return quartzService.resumeTask(taskInfo);
	}
	
	@ApiOperation(value = "启动调度器")
	@PostMapping("/startScheduler")
	public String startScheduler() throws Exception {
		return quartzService.start();
	}
	
	@ApiOperation(value = "关闭调度器")
	@PostMapping("/shutdownScheduler")
	public String shutdownScheduler() throws Exception {
		return quartzService.shutdown();
	}
}
