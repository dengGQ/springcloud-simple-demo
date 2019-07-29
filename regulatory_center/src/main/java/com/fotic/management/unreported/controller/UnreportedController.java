package com.fotic.management.unreported.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.common.util.DateUtils;
import com.fotic.common.util.ExcelTitle;
import com.fotic.common.util.StringUtils;
import com.fotic.management.unreported.entity.ViewUnreported;
import com.fotic.management.unreported.service.IUnreportedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 应报未报
 * 
 * @author zhaoqh
 */
@Controller
@RequestMapping("unreported")
public class UnreportedController {
	// private static Logger logger =
	// LoggerFactory.getLogger(UnreportedController.class.getName());
	
	/**
	 * 应报未报service
	 */
	@Autowired
	IUnreportedService unreportedService;

	/**
	 * 导航到应报未报列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/unreported/index";
	}

	/**
	 * 应报未报分页列表
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页行数
	 * @param startMonth
	 *            开始月
	 * @param endMonth
	 *            结束月
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findPage")
	public Map<String, Object> findPage(@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String startMonth, @RequestParam(required = false) String endMonth,
			@RequestParam(required = false) String dataSrc) {
		if (StringUtils.isNotBlank(startMonth)) {
			Date startDate = DateUtils.getDateMonthFormat(startMonth);
			startMonth = DateUtils.getFirstDayOfMonthStr(startDate);
		}
		if (StringUtils.isNotBlank(endMonth)) {
			Date endDate = DateUtils.getDateMonthFormat(endMonth);
			endMonth = DateUtils.getLastDayOfMonthStr(endDate);
		}
		PageHelper.startPage(pageNumber, pageSize);
		List<ViewUnreported> list = unreportedService.findList(startMonth, endMonth, dataSrc);
		PageInfo<ViewUnreported> p = new PageInfo<ViewUnreported>(list);
		Map<String, Object> map = new HashMap<>();
		map.put("rows", list);
		map.put("total", p.getTotal());
		return map;
	}

	/**
	 * 应报未报数据信息_导出(excel)
	 * @param response
	 * @param startMonth
	 * @param endMonth
	 * @param dataSrc
	 */
	@RequestMapping("/exportDatas")
	public void exportDatas(HttpServletResponse response, @RequestParam String startMonth,
			@RequestParam String endMonth, @RequestParam String dataSrc) {
		if (StringUtils.isNotBlank(startMonth)) {
			Date startDate = DateUtils.getDateMonthFormat(startMonth);
			startMonth = DateUtils.getFirstDayOfMonthStr(startDate);
		}
		if (StringUtils.isNotBlank(endMonth)) {
			Date endDate = DateUtils.getDateMonthFormat(endMonth);
			endMonth = DateUtils.getLastDayOfMonthStr(endDate);
		}
	   
    	List<ViewUnreported> list = unreportedService.findList(startMonth, endMonth, dataSrc);
        try {
        	//设置下载弹出框
        	response.setContentType("application/csv;charset=gbk");
			response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("应报未报信息.csv", "UTF-8"));
			//新建打印输出对象
			PrintWriter out = response.getWriter();
			//写表头
			String[] title = ExcelTitle.unreportedInfoTitle;
			StringBuffer csvWriter = new StringBuffer();
			for (String string : title) {
				csvWriter.append(string+",");
			}
			String str = csvWriter.toString().substring(0, csvWriter.toString().length()-1);
			out.write(str+"\n");
			//写内容
			for (ViewUnreported entity : list) {
				out.write(entity.getIouNo()+","+entity.getCredCode()+","+entity.getConNo()+","+entity.getCoOrgName()+","+entity.getRepayDate()+","+entity.getDataSrc()+"\n");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
