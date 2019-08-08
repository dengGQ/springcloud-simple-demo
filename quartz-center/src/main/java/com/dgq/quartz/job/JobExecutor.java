package com.dgq.quartz.job;

public interface JobExecutor {
	
	/**
	 * @Description: 触发有参任务
	 * @param @param url
	 * @param @param params
	 * @param @return
	 * @param @throws Exception    参数
	 * @return String    返回类型
	 * @throws
	 */
	public String execute(String url, String params) throws Exception;
	
	/**
	 * @Description: 触发无参任务
	 * @param @param url
	 * @param @return
	 * @param @throws Exception    参数
	 * @return String    返回类型
	 * @throws
	 */
	public String execute(String url) throws Exception;
}
