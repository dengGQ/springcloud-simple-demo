package com.dgq.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dgq.users.common.RemoteConfigProperties;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private RemoteConfigProperties configProperties;
	
	@Value("${environment}")
	private String environment;
	
	@Value("${server.port}")
	private Integer port;
	
	@RequestMapping("getRemoteConfigProperties")
	public String getRemoteConfigProperties() {
		return configProperties.getReportPath();
	}
	
	@RequestMapping("getSearchPathsList")
	public List<String> getSearchPathsList() {
		return configProperties.getSearchPathsList();
	}
	
	@RequestMapping("getEnvironment")
	public String getEnvironment() {
		return environment+"-"+port;
	}
}
