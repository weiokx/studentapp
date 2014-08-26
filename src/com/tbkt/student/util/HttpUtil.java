/**
 *
 */
package com.tbkt.student.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂ava联盟</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class HttpUtil {
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();
	public static final String BASE_URL = "http://studentapi.tbkt.cn";
	/**
	 *
	 * @param url 发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				// 创建HttpGet对象。
				HttpGet get = new HttpGet(url);
				// 发送GET请求
				HttpResponse httpResponse = httpClient.execute(get);
				// 如果服务器成功地返回响应
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// 获取服务器响应字符串
					String result = EntityUtils.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * @param url 发送请求的URL
	 * @param params 请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(final String url, final JSONObject params) throws Exception {
		FutureTask<String> task = new FutureTask<String>( new Callable<String>() {
			@Override
			public String call() throws Exception {
				// 创建HttpPost对象。
				HttpPost post = new HttpPost(url);
				// 设置请求参数
				post.setEntity(new StringEntity(params.toString()));
				// 发送POST请求
				HttpResponse httpResponse = httpClient.execute(post);
				// 如果服务器成功地返回响应
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					// 获取服务器响应字符串
					String result = EntityUtils
						.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}
}
