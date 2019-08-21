package com.dgq.quartz.job;

import java.util.function.Consumer;

import com.dgq.quartz.util.OkHttpClientUtil;

public interface JobExecutor {
	
	/**
	 * @Description: 同步触发有参http任务
	 * @param @param url
	 * @param @param params
	 * @param @return
	 * @param @throws Exception    参数
	 * @return String    返回类型
	 * @throws
	 */
	public String execute(String url, String params) throws Exception;
	
	/**
	 * @Description: 同步触发无参http任务
	 * @param @param url
	 * @param @return
	 * @param @throws Exception    参数
	 * @return String    返回类型
	 * @throws
	 */
	public String execute(String url) throws Exception;

	/**
	 * @Description: 异步触发http任务
	 * @param @param url
	 * @param @param params
	 * @param @param consumer 回调
	 * @param @return
	 * @param @throws Exception    参数
	 * @return String    返回类型
	 * @throws
	 */
	public void submit(String url, String params, Consumer<OkHttpClientUtil.ResponseResult> consumer) throws Exception;
}
