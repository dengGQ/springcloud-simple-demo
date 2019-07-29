package com.fotic.common.httpclient.service.impl;

/*
 * @Description: public class HttpClientService{ }
 * @author dgq 
 * @date 2018年4月20日
 */

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.sf.json.JSONArray;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.httpclient.HttpResult;
import com.fotic.common.httpclient.service.HttpClientService;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.sms.service.SmsSendService;

@Service("httpClientService")
public class HttpClientServiceImpl implements HttpClientService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;
	@Autowired
	private SmsSendService sendService;

	/**
	 * 无参Get请求
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);

		httpGet.setConfig(this.requestConfig);
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} finally {
			if (Objects.nonNull(response)) {
				response.close();
			}
		}
		return null;
	}

	/**
	 * 有参Get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url, Map<String, String> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder uriBuilder = new URIBuilder(url);

		for (String key : params.keySet()) {
			uriBuilder.addParameter(key, params.get(key));
		}

		return doGet(uriBuilder.build().toString());
	}

	/**
	 * 有参 post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> params)
			throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);
		if (params != null) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, params.get(key)));
			}

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);

			httpPost.setEntity(entity);
		}

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "utf-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 无参POST请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url) throws IOException {
		return this.doPost(url, null);
	}

	/**
	 * 提交json数据
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public HttpResult doPostJson(String url, String json)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);
		//JSONArray jsonObject = new JSONArray(json);
		JSONArray jsonObject = JSONArray.fromObject(json);
		Calendar cale = null;
		cale = Calendar.getInstance();
		// 获取当月第一天和最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime()) + " 00:00:00";
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime()) + " 24:00:00";
		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
		// 对同步代发短信做筛选，本月相同业务号的数据存在便不再同步
		CloseableHttpResponse response = null;
	/*	for (int i = 0; i < jsonObject.size(); i++) {
			json = jsonObject.getString(i).toString();

			if (Objects.nonNull(json)) {
				StringEntity entity = new StringEntity(json,
						ContentType.APPLICATION_JSON);
				httpPost.setEntity(entity);
			}
			
			try {
				response = httpClient.execute(httpPost);

				return new HttpResult(response.getStatusLine().getStatusCode(),
						EntityUtils.toString(response.getEntity(), "utf-8"));
			} finally {
				if (Objects.nonNull(response)) {
					response.close();
				}
			}

		}*/
		

		if (Objects.nonNull(json)) {
			StringEntity entity = new StringEntity(json,
					ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
		}
		
		try {
			response = httpClient.execute(httpPost);

			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "utf-8"));
		} finally {
			if (Objects.nonNull(response)) {
				response.close();
			}
		}

		
		/*return new HttpResult(response.getStatusLine().getStatusCode(),
				EntityUtils.toString(response.getEntity(), "utf-8"));	*/

	}
}
