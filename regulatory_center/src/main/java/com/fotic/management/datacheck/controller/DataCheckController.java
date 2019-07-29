package com.fotic.management.datacheck.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.base.controller.BaseController;
import com.fotic.common.util.ZipUtils;
import com.fotic.management.datacheck.entity.SelfCheckExprt;
import com.fotic.management.datacheck.service.IDataCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 自检数据查看
 * @author WangTao 
 * @since 2017-06-20
 *
 */
@Controller
@RequestMapping("datacheck")
public class DataCheckController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(DataCheckController.class.getName());
	
	@Autowired
	private IDataCheckService dataCheckService;

	/**
	 * 自检数据查询首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		return "/datacheck/dataCheckIndex";
	}
	
	/**
	 * 自检_跳转页面
	 * 此功能暂缓
	 * @return
	 */
	/*@RequestMapping("/selfCheck")
	public String selfCheck(){
		return "/datacheck/selfCheck";
	}*/
	
	/**
	 * 自检数据查询--列表数据
	 * @param pageNumber
	 * @param pageSize
	 * @param quarter
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryDataCheck")
	public Map<String, Object>  queryDataCheck(
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,
	        @RequestParam(required=false,defaultValue="") String year) {
		log.info("执行自检数据查询功能。");
		PageHelper.startPage(pageNumber, pageSize);
	    List<SelfCheckExprt> list = dataCheckService.queryDataCheck(year);
	    PageInfo<SelfCheckExprt> p=new PageInfo<SelfCheckExprt>(list);
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("rows", list);
	    map.put("total", p.getTotal());
		return map;
	}
	
	/**
	 * 自检数据文件导出（打包成zip文件）
	 * @param ids 要导出文件的数据序号（可能是多个，以逗号分隔）
	 * @throws IOException 
	 */
	@RequestMapping("/exportSelfCheckDataFile")
	public void exportSelfCheckDataFile(@RequestParam(required=false) String quatr ,HttpServletResponse response) throws IOException{
		log.info("导出自检数据文件。");
		String zipFileName  = "自检数据导出-"+new Date().getTime() +".zip";
		response.setContentType("application/octet-stream");  
        response.setCharacterEncoding("UTF-8");  
        response.setHeader("Content-Disposition", "attachment; filename="+new String(zipFileName.getBytes("gbk"),"iso-8859-1")); 
		List<File> fileList = dataCheckService.exportSelfCheckDataFileByQuatr(quatr);
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for(File txtFile : fileList){
                ZipUtils.doCompress(txtFile, out);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
        log.info("执行自检数据查询功能，文件导出成功，文件名 :" + zipFileName);
	}
	
	/**
	 * 清理数据
	 * @return
	 */
	@RequestMapping("dataClear")
	@ResponseBody
	public String dataClear(){
		try {
			this.dataClearpProcedure("PKG_RHZX_CLEAR.main");
			return "清理成功！";
		} catch (Exception e) {
			e.printStackTrace();
			return "清理失败！";
		}
	}
}
