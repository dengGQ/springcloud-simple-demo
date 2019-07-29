package com.fotic.common.quartz.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fotic.common.quartz.QuartzManager;
import com.fotic.common.quartz.dao.QuartzDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/*
* @Description: public class QuartzManagerController{ }
* @author dgq 
* @date 2018年4月26日
*/
@Controller
@RequestMapping("qm")
public class QuartzManagerController {
	
	private static Logger logger = LoggerFactory.getLogger(QuartzManagerController.class);
	
	@Resource
	private QuartzManager quartzManager;
	
	@Autowired
	private QuartzDao quartzDao;
	
	
	@RequestMapping("quartzManagerPage")
	public String quartzManagerPage(){
		return "/qm/quartz_manager_page";
	}
	
	@RequestMapping("startSchedule")
	@ResponseBody
	private String startSchedule(){
		logger.info("启动定时器.....................");
		if(quartzManager.start()){
			return "定时器启动成功！";
		}else{
			return "已停止的定时器无法再启动！";
		}
	}
	
	@RequestMapping("shutdown")
	@ResponseBody
	private String shutdown(){
		logger.info("停止定时器.....................");
		return quartzManager.shutdown() ? "定时器停止成功" : "定时器停止失败！";
	}
	
	@RequestMapping(value="/pauseJob",method=RequestMethod.GET)
	@ResponseBody
	public String pauseJob(String jobName){
		try {
			quartzManager.pauseJob(jobName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	@RequestMapping(value="/resumeJob", method=RequestMethod.GET)
	@ResponseBody
	public String resumeJob(String jobName){
		try {
			quartzManager.resumeJob(jobName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	@RequestMapping(value="/startJob", method=RequestMethod.GET)
	@ResponseBody
	public String startJob(){
		 Date day=new Date();    

		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 String startDate   = df.format(day);
		 System.out.println(df.format(day));  
		try {
			quartzDao.startJob(startDate);
		
			quartzDao.insertJob2(startDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	@RequestMapping(value="/stopJob", method=RequestMethod.GET)
	@ResponseBody
	public String stopJob(){
		 Date day=new Date();    

		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 String stopDate   = df.format(day);
		 System.out.println(df.format(day));  
		try {
			Integer ints = quartzDao.insertJob3();
			if(ints == null){
				ints = 1;
			}else {
				ints+= ints;
			}
			String numString = ints+"";
			quartzDao.stopJob(stopDate);
			quartzDao.insertJob(stopDate,numString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	@RequestMapping(value="/addJob", method=RequestMethod.GET)
	@ResponseBody
	public String addJob(String jobName, String jobClassPath, String cronExpression){
		try {
			quartzManager.addJob(jobName, jobClassPath, cronExpression);
		} catch (ClassNotFoundException e) {
			logger.info("job 类："+jobClassPath+", 必须继承org.quartz.job");
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	@RequestMapping(value="/delJob", method=RequestMethod.GET)
	@ResponseBody
	public String delJob(String jobName){
		try {
			quartzManager.deleteJob(jobName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	/**
	 * 修改作业计划
	 * @param triggerName
	 * @param cron
	 * @return
	 */
	@RequestMapping(value="/editCron", method=RequestMethod.GET)
	@ResponseBody
	public String editCron(String triggerName, String cron){
		try {
			quartzManager.updateTrigger(triggerName, cron);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "执行计划以改变，请注意！";
	}
	
	@RequestMapping(value="/jobList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jobList(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize){
		PageHelper.startPage(pageNumber, pageSize);

		List<Map<String,Object>> jobList = quartzDao.jobList();
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(jobList);
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("rows", jobList);
		map.put("total", pageInfo.getTotal());
		return map;
    }
	
	@RequestMapping(value="/jobListBtn", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jobListBtn(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize){
		PageHelper.startPage(pageNumber, pageSize);

		List<Map<String,Object>> jobList = quartzDao.jobList();
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(jobList);
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("rows", jobList);
		map.put("total", pageInfo.getTotal());
		return map;
    }
}
