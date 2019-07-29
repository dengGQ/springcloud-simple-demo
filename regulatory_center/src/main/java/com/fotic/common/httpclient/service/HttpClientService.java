package com.fotic.common.httpclient.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fotic.common.httpclient.HttpResult;

/*
* @Description: public class HttpClientService{ }
* @author dgq 
* @date 2018年4月23日
*/
public interface HttpClientService {
	
	/**
	 * 无参Get请求 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) throws ClientProtocolException, IOException;
	
	/**
	 * 有参Get请求
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url, Map<String, String> params) throws URISyntaxException, ClientProtocolException, IOException;
	
	/**
	 * 有参 post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> params) throws IOException;
	
	/**
	 * 无参POST请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url) throws IOException;
	
	/**
	 * 提交json数据
	 * @param url
	 * @param json
	 * @return HttpResult： status data
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPostJson(String url, String json) throws ClientProtocolException, IOException;
}
