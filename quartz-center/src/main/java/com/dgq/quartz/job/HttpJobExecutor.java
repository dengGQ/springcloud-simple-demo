package com.dgq.quartz.job;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.dgq.quartz.util.HttpClientUtil;
import com.dgq.quartz.util.OkHttpClientUtil;
import com.dgq.quartz.util.OkHttpClientUtil.ResponseResult;

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
	
	@Override
	public void submit(String url, String params, Consumer<ResponseResult> consumer) throws Exception {
		OkHttpClientUtil.doPost(url, "application/json", params, consumer);
	}
}
