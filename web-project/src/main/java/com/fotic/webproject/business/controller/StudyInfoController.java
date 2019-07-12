package com.fotic.webproject.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fotic.webproject.business.entity.StudyInfo;
import com.fotic.webproject.business.service.StudyInfoService;
import com.fotic.webproject.web.BaseController;
import com.fotic.webproject.web.CommonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="外派选学接口")
@RestController
@RequestMapping("/studyInfo")
public class StudyInfoController extends BaseController{
	
	@Autowired
	private StudyInfoService ss;
	
	@ApiOperation(value="任意条件检索外派选学", response=CommonResult.class, httpMethod="POST")
	@RequestMapping("/find")
	public CommonResult findStudyInfo(@RequestBody StudyInfo si){
		List<StudyInfo> data = ss.findAll(si);
		return CommonResult.build(data);
	}
	
	@ApiOperation(value="新增外派选学", response=CommonResult.class, httpMethod="POST")
	@RequestMapping("/add")
	public CommonResult addStudyInfo(@RequestBody StudyInfo si) {
		StudyInfo studyInfo = ss.insert(si);
		return CommonResult.build(studyInfo);
	}
	
	@ApiOperation(value="修改", response=CommonResult.class, httpMethod="POST")
	@RequestMapping("/update")
	public CommonResult updateStudyInfo(@RequestBody StudyInfo si) {
		StudyInfo studyInfo = ss.update(si);
		return CommonResult.build(studyInfo);
	}
	
	@ApiOperation(value="delete", response=CommonResult.class, httpMethod="POST")
	@RequestMapping("/delete")
	public CommonResult deleteStudyInfo(@RequestBody StudyInfo si) {
		StudyInfo studyInfo = ss.update(si);
		return CommonResult.build(studyInfo);
	}
}
