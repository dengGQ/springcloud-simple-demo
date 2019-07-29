package com.fotic.management.errormessage.controller;
/** 
 * @author  顾亚玺
 * @date 创建时间：2017年7月14日 下午2:59:57 
 * @version 1.0 * @parameter  
 */

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.common.enums.CommonEnum.StatusEnum;
import com.fotic.common.util.DateUtils;
import com.fotic.common.util.ExcelTitle;
import com.fotic.management.errormessage.entity.CheckResult;
import com.fotic.management.errormessage.service.ICheckResultService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = { "error" })
public class ErrorMessageController {

	@Autowired
	private ICheckResultService checkResultService;

	/**
	 * 错误信息查询_跳转页面
	 */
	@RequestMapping(value = { "index" })
	private String index() {
		return "/error/index";
	}

	/**
	 * 错误信息查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param contractNo
	 *            合同号
	 * @param credNo
	 *            证件号码
	 * @param orgNo
	 *            机构代码
	 * @param credType
	 *            证件类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "errorMsgPage" })
	private Map<String, Object> errorMsgPage(@RequestParam(required = true, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) String iouNo, @RequestParam(required = false) String credCode,
			@RequestParam(required = false) String coOrgNo, @RequestParam(required = false) String credType,
			@RequestParam(required = false) String dataSrc) {
		PageHelper.startPage(pageNumber, pageSize);
		List<CheckResult> findAll = checkResultService.findList(coOrgNo, iouNo, credType, credCode, dataSrc);
		PageInfo<CheckResult> p = new PageInfo<CheckResult>(findAll);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", p.getTotal());
		map.put("rows", findAll);
		return map;
	}

	/**
	 * 错误信息导出CSV文件，并下载
	 * 
	 * @param response
	 * @param iouNo
	 * @param credCode
	 * @param coOrgNo
	 * @param credType
	 * @param dataSrc
	 */
	@RequestMapping("/exportErrorDatas")
	public void exportErrorDatas(HttpServletResponse response, @RequestParam(required = false) String iouNo,
			@RequestParam(required = false) String credCode, @RequestParam(required = false) String coOrgNo,
			@RequestParam(required = false) String credType, @RequestParam(required = false) String dataSrc) {
		List<CheckResult> list = checkResultService.findList(coOrgNo, iouNo, credType, credCode, dataSrc);
		try {
			// 设置下载弹出框
			response.setContentType("application/csv;charset=gbk");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("错误信息.csv", "UTF-8"));
			// 新建打印输出对象
			PrintWriter out = response.getWriter();
			// 写表头
			String[] title = ExcelTitle.errorInfoTitle;
			StringBuffer csvWriter = new StringBuffer();
			for (String string : title) {
				csvWriter.append(string + ",");
			}
			String str = csvWriter.toString().substring(0, csvWriter.toString().length() - 1);
			out.write(str + "\n");
			// 写内容
			for (CheckResult entity : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(entity.getRuleNo() + "," + DateUtils.formatDate(entity.getCheckDate(), "yyyy-MM-dd") + ","
						+ entity.getCheckType() + "," + entity.getDataSrc() + "," + entity.getCoOrgCode() + ","
						+ entity.getConNo() + "," + entity.getIouNo() + "," + entity.getCredType() + ","
						+ entity.getCredCode() + "," + entity.getTableName() + "," + entity.getColumnName() + ","
						+ entity.getColumnCname() + "," + entity.getRuleDesc() + "\n");
				out.write(sb.toString());
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 错误信息_删除
	 * 
	 * @param iouNo
	 *            业务号
	 * @param credCode
	 *            证件号吗
	 * @param coOrgNo
	 *            机构号
	 * @param credType
	 *            证件类型
	 * @param dataSrc
	 *            数据来源
	 * @return
	 */
	@RequestMapping("/delSelectedDatas")
	@ResponseBody
	public Map<String, Object> delSelectedDatas(HttpServletRequest request) {
		String coOrgNo = request.getParameter("coOrgNo");
		String iouNo = request.getParameter("iouNo");
		String credType = request.getParameter("credType");
		String credCode = request.getParameter("credCode");
		String dataSrc = request.getParameter("dataSrc");
		Map<String, Object> map = new HashMap<>();
		try {
			checkResultService.delBySelected(coOrgNo, iouNo, credType, credCode, dataSrc);
			map.put("status", StatusEnum.SUCCESS.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", StatusEnum.FAIL.getStatus());
			map.put("msg", "数据库执行失败");
		}
		return map;
	}

}
