package com.dgq.quartz.job;

import org.springframework.stereotype.Component;

import com.dgq.quartz.util.HttpClientUtil;

@Component("http")
public class HttpJobExecutor implements JobExecutor{

	@Override
	public String execute(String url, String params) throws Exception {
		return HttpClientUtil.doPost(url, "application/json", params);
	}

	@Override
	public String execute(String url) throws Exception {
		return HttpClientUtil.doPost(url, "application/json", null);
	}
}
