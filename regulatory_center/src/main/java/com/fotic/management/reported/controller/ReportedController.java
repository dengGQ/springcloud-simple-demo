package com.fotic.management.reported.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.management.reported.entity.SubmitPerTrade;
import com.fotic.management.reported.service.IReportedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 上报(数据推送)业务
 * @author zhaoqh
 */
@Controller
@RequestMapping("reported")
public class ReportedController {
	private static Logger logger = LoggerFactory.getLogger(ReportedController.class.getName());

	/**
	 * 上报service
	 */
	@Autowired
	private IReportedService reportedService;
	
	/**
	 * 导航到列表页面
	 * @param dataSrc 数据来源 1:信贷, 2:CSV
	 * @return
	 */
	@RequestMapping("/index")
	public String index(@RequestParam(required=true,defaultValue="1") Integer dataSrc, Model model){
		logger.info("打开上报页面");
		List<SubmitPerTrade> listOrg = reportedService.findListOrg(dataSrc);
		model.addAttribute("coOrgNameList",listOrg);
		model.addAttribute("dataSrc", dataSrc);
		return "/reported/index";
	}
	
	/**
	 * 上报页面列表
	 * @param dataSrc 数据来源 1:信贷, 2:CSV
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPage")
	public Map<String, Object>  findPage(
			@RequestParam(required=true,defaultValue="1") Integer dataSrc,
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize ) {
		PageHelper.startPage(pageNumber, pageSize);
	    List<SubmitPerTrade> list = reportedService.findList(dataSrc);
	    
	    List<SubmitPerTrade> listOrg = reportedService.findListOrg(dataSrc);
	    /**for (int i = 0; i < list.size(); i++) {
			Date date = list.get(i).getInsertDttm();
		}*/
	    PageInfo<SubmitPerTrade> p=new PageInfo<SubmitPerTrade>(list);
	    Map<String, Object> map = new HashMap<>();
	    map.put("rows", list);
	    map.put("coOrgNameList", listOrg);
	    map.put("total", p.getTotal());
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/findPageAll")
	public Map<String, Object>  findPageAll(
			String startDate ,String endDate ,String coOrgName,
			@RequestParam(required=true,defaultValue="1") Integer dataSrc,
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize ) {
		PageHelper.startPage(pageNumber, pageSize);
	    List<SubmitPerTrade> list = reportedService.findListAll(startDate,endDate,dataSrc,coOrgName);
	    List<SubmitPerTrade> listOrg = reportedService.findListOrg(dataSrc);
	    for (int i = 0; i < list.size(); i++) {
	    	String string = list.get(i).getInsertDttm();
			if (string == null) {
				list.get(i).setInsertDttm(startDate+"至"+endDate);
			};
		}
	    PageInfo<SubmitPerTrade> p=new PageInfo<SubmitPerTrade>(list);
	    Map<String, Object> map = new HashMap<>();
	    map.put("rows", list);
	    map.put("coOrgNameList", listOrg);
	    map.put("total", p.getTotal());
		return map;
	}
	
	/**
	 * 上报(数据推送)操作
	 * @param insertDates	日期:类型,日期:类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reporting")
	public Map<String, Object> reporting(
			@RequestParam(required=true) String insertDates,HttpServletRequest request){
		return reportedService.reporting(insertDates,request);
		
	}
}
