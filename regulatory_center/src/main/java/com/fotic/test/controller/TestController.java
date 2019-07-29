package com.fotic.test.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fotic.test.model.Test;
import com.fotic.test.service.ITestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class.getName());
	@Autowired
	private ITestService testService;
	
	@RequestMapping(value = "test/saveTest")
	public @ResponseBody Test saveTest() {
		Test test = new Test();
		test.setName("王五");
		test.setCrateDate(new Date());
		test.setAge(Short.valueOf("21"));
		  testService.save(test);
		return test;
	}

	@RequestMapping(value = "test/getTest")
	public @ResponseBody Test getTest(@RequestParam(name = "id") Long id) {
		logger.info("按ID查询test");
		Test test = testService.get(id);
		
		return test;
	}
	
	@RequestMapping(value = "test/getTest1")
	public @ResponseBody String getTest1(@RequestParam(name = "id") Long id) {
		logger.info("按ID查询test");
		Test test = testService.get(id);
		
		return test.getName();
	}
	
	@RequestMapping(value = "test/getName"/*,  produces = "application/json; charset=utf-8"*/)
	public @ResponseBody String getName(@RequestParam(name = "id") Long id) {
		logger.info("getName");
		Map<String, String> map = testService.getName(id);
		return map.get("name");
	}
	
	@RequestMapping(value = "test/getNameById"/*,  produces = "application/json; charset=utf-8"*/)
	public @ResponseBody String getNameById(@RequestParam(name = "id") Long id) {
		logger.info("getNameById");
		String name = testService.getNameById(id);
		return name;
	}
	
	@RequestMapping(value = "test/getPage")
	public @ResponseBody PageInfo<Test> getPage( 
		    @RequestParam(required=true,defaultValue="1") Integer page,
            @RequestParam(required=false,defaultValue="10") Integer pageSize){

        PageHelper.startPage(page, pageSize);
        List<Test> list = testService.findAll();

        PageInfo<Test> p=new PageInfo<Test>(list);
      //System.out.println(p.getList());

		return p;
	}

	@RequestMapping(value = "test/getData")
	public @ResponseBody List getData(@RequestParam(name = "id") Long id) {
    List tests = new ArrayList();
		Test test = testService.get(id);
		int totalCount = 1;
		tests.add(test);
		return tests;
	}

	@ResponseBody
	@RequestMapping(value = { "test/searchListByPage" })
	public Map searchListByPage(HttpServletRequest request) {
		
		int offset = request.getParameter("offset") == null ? 1 : Integer.parseInt(request.getParameter("offset"));

		int limit = request.getParameter("limit") == null ? 10 : Integer.parseInt(request.getParameter("limit"));
		
		String sort = request.getParameter("sort") == null ? "" : request.getParameter("sort");
		
		String order = request.getParameter("order") == null ? "" : request.getParameter("order");
		
		String searchuserName = request.getParameter("searchuserName") == null ? "" : request.getParameter("searchuserName");
	
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", testService.findAll().size());
		map.put("rows", this.testService.findAll());
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = { "test/tableEdit" })
	public Map tableEdit(HttpServletRequest request) {		

		Map<String, Object> map = new HashMap<String, Object>();

		return map;
	}
}
