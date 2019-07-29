package com.fotic.management.publicquery.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.common.util.PubMethod;
import com.fotic.management.publicquery.entity.Project;
import com.fotic.management.publicquery.entity.RhzxContract;
import com.fotic.management.publicquery.entity.RhzxOrg;
import com.fotic.management.publicquery.entity.RhzxProdure;
import com.fotic.management.publicquery.service.PublicQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
* @Title: PublicQueryController.java 
* @Package com.fotic.management.publicquery.controller 
* @Description: 公共查询 
* @author 王明月   
* @date 2017年7月11日 下午2:13:02
 */
@Controller
@RequestMapping("publicquery")
public class PublicQueryController {
	private static Logger logger = LoggerFactory.getLogger(PublicQueryController.class);
	@Autowired
	private PublicQueryService publicQueryService;

	@RequestMapping(value = { "project" })
	public String project(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-项目查询页面");
		return "/publicquery/project";
	}
	
	@RequestMapping(value = { "projectNameAndId" })
	public String projectNameAndId(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-项目查询页面");
		return "/publicquery/projectNameAndId";
	}
	
	@RequestMapping(value = { "org" })
	public String org(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-机构查询页面");
		return "/publicquery/org";
	}
	
	@RequestMapping(value = { "orgNameAndCode" })
	public String orgNameAndCode(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-机构查询页面");
		return "/publicquery/orgNameAndCode";
	}
	
	@RequestMapping(value = { "product" })
	public String product(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-产品查询页面");
		return "/publicquery/product";
	}
	
	@RequestMapping(value = { "productAndOrg" })
	public String productAndOrg(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-产品查询页面");
		return "/publicquery/productAndOrg";
	}
	
	@RequestMapping(value = { "contract" })
	public String contract(HttpServletRequest request, HttpServletResponse response) {
		logger.info("查询条件-合同查询页面");
		return "/publicquery/contract";
	}
	


	/**
	 * 根据项目名称查询
	 * @param projectName
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "projectSearch" })
	public Map<String, Object> projectSearch(
			@RequestParam(required=false) String projectName,
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize) {
		logger.info("查询条件-项目查询");
		PageHelper.startPage(pageNumber, pageSize);		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Project> findAllProject = publicQueryService.findAllProject(projectName);
		PageInfo<Project> p=new PageInfo<Project>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	
	/**
	 * 根据项目名称或项目编号查询
	 * @param projNameOrId 项目名称或编号
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "findRhzxOrgByNameOrId" })
	public Map<String, Object> findRhzxOrgByNameOrId(
			@RequestParam(required=false) String projNameOrId,
			@RequestParam(required=true,defaultValue="1") Integer pageNumber,
			@RequestParam(required=false,defaultValue="10") Integer pageSize) {
		logger.info("查询条件-项目查询");
		PageHelper.startPage(pageNumber, pageSize);		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Project> findAllProject = new ArrayList<>();
		try {
			projNameOrId = URLDecoder.decode(projNameOrId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(PubMethod.isEmpty(projNameOrId) || "-1".equals(projNameOrId) || "CSV导入".contains(projNameOrId)
				||"cSV导入".contains(projNameOrId)||"CsV导入".contains(projNameOrId)
				||"CSv导入".contains(projNameOrId)||"csV导入".contains(projNameOrId)
				||"Csv导入".contains(projNameOrId)||"cSv导入".contains(projNameOrId)
				||"csv导入".contains(projNameOrId)){
			findAllProject = publicQueryService.findRhzxOrgByNameOrId(projNameOrId);
			Project project = new Project();
			project.setProjId("CSV导入");
			project.setProjName("CSV导入");
			findAllProject.add(project);
		}else{
			findAllProject = publicQueryService.findRhzxOrgByNameOrId(projNameOrId);
		}
		PageInfo<Project> p=new PageInfo<Project>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	
	/**
	 * 机构查询
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "orgSearch" })
	public Map<String, Object> orgSearch(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,@RequestParam(required=false) String orgName
	        ) {
		PageHelper.startPage(pageNumber, pageSize);		
		Map<String, Object> map = new HashMap<String, Object>();
		List<RhzxOrg> findAllProject = publicQueryService.findAllRhzxOrg(orgName);
		PageInfo<RhzxOrg> p=new PageInfo<RhzxOrg>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	
	/**
	 * 机构查询(根据机构名称或者机构编号查询)
	 * @param pageNumber
	 * @param pageSize
	 * @param orgName 机构编号或者机构名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "findRhzxOrgByNameOrCode" })
	public Map<String, Object> findRhzxOrgByNameOrCode(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
			@RequestParam(required=false,defaultValue="10") Integer pageSize,@RequestParam(required=false) String orgNameOrCode
			) {
		PageHelper.startPage(pageNumber, pageSize);		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			orgNameOrCode = URLDecoder.decode(orgNameOrCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<RhzxOrg> findAllProject = publicQueryService.findRhzxOrgByNameOrCode(orgNameOrCode);
		PageInfo<RhzxOrg> p=new PageInfo<RhzxOrg>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	/**
	 * 产品查询
	 * @param pageNumber
	 * @param proName
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "productSearch" })
	public Map<String, Object> productSearch(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
			@RequestParam String proName,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		PageHelper.startPage(pageNumber, pageSize);		
		Map<String, Object> map = new HashMap<String, Object>();
		List<RhzxProdure> findAllProject = publicQueryService.findAllRhzxProdure(proName);
		PageInfo<RhzxProdure> p=new PageInfo<RhzxProdure>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	/**
	 * 过产品名称或者合作机构名称查询产品和合作机构
	 * @param pageNumber
	 * @param projNameOrOrgName 合作机构或产品名称
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findProductAndOrgByProjNameOrOrgName", method = RequestMethod.GET)
	public Map<String, Object> findProductAndOrgByProjNameOrOrgName(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
			@RequestParam String projNameOrOrgName,
			@RequestParam(required=false,defaultValue="10") Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		PageHelper.startPage(pageNumber, pageSize);		
		
		try {
			projNameOrOrgName = URLDecoder.decode(projNameOrOrgName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		List<RhzxProdure> findAllProject = new ArrayList<>();
		if(PubMethod.isEmpty(projNameOrOrgName) || "-1".equals(projNameOrOrgName) || "CSV导入".contains(projNameOrOrgName)
				||"cSV导入".contains(projNameOrOrgName)||"CsV导入".contains(projNameOrOrgName)
				||"CSv导入".contains(projNameOrOrgName)||"csV导入".contains(projNameOrOrgName)
				||"Csv导入".contains(projNameOrOrgName)||"cSv导入".contains(projNameOrOrgName)
				||"csv导入".contains(projNameOrOrgName)){
			findAllProject = publicQueryService.findProductAndOrgByProjNameOrOrgName(projNameOrOrgName);
			RhzxProdure product = new RhzxProdure();
			product.setProdCode("-1");
			product.setProdName("CSV导入");
			findAllProject.add(product);
		}else{
			findAllProject = publicQueryService.findProductAndOrgByProjNameOrOrgName(projNameOrOrgName);
		}
		PageInfo<RhzxProdure> p=new PageInfo<RhzxProdure>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	
	/**
	 * 合同查询
	 * @param pageNumber
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "contractSearch" })
	public Map<String, Object> contractSearch(@RequestParam(required=true,defaultValue="1") Integer pageNumber,
	        @RequestParam(required=false,defaultValue="10") Integer pageSize,@RequestParam String conNo) {
		logger.info("查询条件-合同查询");
		PageHelper.startPage(pageNumber, pageSize);		
		Map<String, Object> map = new HashMap<String, Object>();
		List<RhzxContract> findAllProject = publicQueryService.findAllRhzxContract(conNo);
		PageInfo<RhzxContract> p=new PageInfo<RhzxContract>(findAllProject);
		map.put("total", p.getTotal());
		map.put("rows", findAllProject);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = { "getproject" })
	public String getproject(@RequestParam(required=true,defaultValue="") long projNum) {
		Project project = new Project();
		project.setProjId(String.valueOf(projNum));
		Project proj = this.publicQueryService.getProject(project);
		return proj.getProjName();
	}
	
}