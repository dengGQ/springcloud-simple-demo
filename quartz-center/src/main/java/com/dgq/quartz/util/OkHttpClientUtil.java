package com.dgq.quartz.util;

import java.io.IOException;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 * @ClassName: OkHttpClientUtil
 * @Description: OkHttp客户端，异步GET/POST
 * @author dgq
 * @date 2019年8月20日
 */
public class OkHttpClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static void doGet(String url, String contentType, String content, Consumer<ResponseResult> consumer) throws Exception {
		Request request = new Request.Builder().url(url).build();
		request(request, consumer);
	}
	
	public static void doPost(String url, String contentType, String content, Consumer<ResponseResult> consumer) throws Exception {
		Request request = new Request.Builder()
			.url(url)
			.post(RequestBody.create(content, MediaType.parse(contentType)))
			.build();
		request(request, consumer);
	}
	
	private static void request(Request request,Consumer<ResponseResult> consumer) {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				log.info("messsage--{}, isSuccessful--{}, code--{}", response.message(), response.isSuccessful(), response.code());
				consumer.accept(ResponseResult.of(RequestStatusEnum.SUCCESS, response.code(), response.body().toString()));
			}
			@Override
			public void onFailure(Call call, IOException exception) {
				ResponseResult responseResult = ResponseResult.ofPart(RequestStatusEnum.FAIL, CommonUtil.getExceptionDetail(exception));
				consumer.accept(responseResult);
			}
		});
	}
	
	@Data
	@AllArgsConstructor
	public static class ResponseResult{
		private RequestStatusEnum requestStatus;
		private Integer httpCode;
		private String responseBody;
		public static ResponseResult of(RequestStatusEnum requestStatus, Integer httpCode, String responseBody) {
			return new ResponseResult(requestStatus, httpCode, responseBody);
		}
		public static ResponseResult ofPart(RequestStatusEnum requestStatus, String responseBody) {
			return new ResponseResult(requestStatus, null, responseBody);
		}
	}
	
	public static enum RequestStatusEnum{
		SUCCESS,FAIL
	}
	
	public static void main(String[] args) throws Exception {
		doPost("http://localhost:8080", "application/json;cahrset=utf-8", "{dgq:123}", responseResult->{
			try {
				log.info("请求响应.............{}", responseResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
