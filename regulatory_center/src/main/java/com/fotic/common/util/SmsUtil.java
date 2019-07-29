package com.fotic.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fotic.common.constant.Constant;

/**
 * 短信发送的工具类
 *
 */
public class SmsUtil {

	private static String inf_url = PropertiesUtil.get("sms_inf_url");
	private static String username = PropertiesUtil.get("sms_username");
	private static String password = PropertiesUtil.get("sms_password");
	private static String epid = PropertiesUtil.get("sms_epid");
	
	private static String moduleSmsUsername = PropertiesUtil.get("module_sms_username");
	private static String moduleSmsPassword = PropertiesUtil.get("module_sms_password");
	private static String moduleSmsEpid = PropertiesUtil.get("module_sms_epid");

	private static String linkid = "";// 商户唯一标示 20 位长度
	private static String subcode = "";//

	/**
	 * 发送短信接口
	 * @param phone
	 * @param message
	 * @param linkid
	 * @return
	 */
	public static String SmsSend(String phone, String message,String smsModuleType){
		try {
			
			String requestAddr = assembleRequestParam(phone, message,smsModuleType);
			String returnCode = sendGet(requestAddr);
			
			System.out.println("returnCode:" + returnCode);
			return returnCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String sendGet(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		BufferedReader in = null;
		try {
			HttpGet get = new HttpGet(url);
			System.out.println(url);
			CloseableHttpResponse httpResponse = null;
			httpResponse = httpClient.execute(get);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					String tempStr = "";
					in = new BufferedReader(new InputStreamReader(entity.getContent()));
					System.out.println(entity.getContent().toString());
					StringBuffer content = new StringBuffer();
					while ((tempStr = in.readLine()) != null) {
						System.out.println(tempStr);
						content.append(tempStr);
					}
					return content.toString();
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	private static String assembleRequestParam(String phone, String message,String smsModuleType) throws Exception{
		Map<String, String> pmap = new HashMap<String, String>();
		if(smsModuleType.equals(Constant.SMS_MODELE_TYPE)){
			pmap.put("username", moduleSmsUsername);
			pmap.put("password", moduleSmsPassword);
			pmap.put("epid", moduleSmsEpid);
		}else if(smsModuleType.equals(Constant.SMS_NORMAL_TYPE)){
			pmap.put("username", username);
			pmap.put("password", password);
			pmap.put("epid", epid);
		}
		pmap.put("phone", phone);
		pmap.put("message", URLEncoder.encode(message, "gb2312"));
		pmap.put("linkid", linkid);
		pmap.put("subcode", subcode);
		String pstr = "";
		for (Map.Entry<String, String> entry : pmap.entrySet()) {
			pstr += "&" + entry.getKey() + "=" + entry.getValue();
		}
		pstr = pstr.substring(1);
		
		return inf_url + "?" + pstr;
	}
}
