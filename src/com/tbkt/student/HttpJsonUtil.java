package com.tbkt.student;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Description:
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  xuwei
 * @version  1.0
 */
class HttpJsonUtil implements Runnable{  
    private String method;  
    private String url;  
    private JSONObject params;  
    private Handler handler;
    private int what;
      
    public HttpJsonUtil(String method, String url, JSONObject params, Handler handler, int what) {  
        super();  
        this.method = method; 
        this.url = url;  
        this.params = params;  
        this.handler = handler;  
        this.what = what;
    }  
  
    @Override  
    public void run() {  
    	try {
	        Message m = new Message();  
	        m.what = what;  
	        if(method.equals("GET")){  
	        	
	            Log.i("iiiiiii","发送GET请求");  
	            //m.obj = GetPostUtil.sendGet(url, params);  
	            //JSONObject jsonRes = new JSONObject(result);  // 解析sjon格式数据
	            //JSONObject jsonData = jsonRes.getJSONObject("data");
	        }  
	        if(method.equals("POST")){  
	        	HttpPost request = new HttpPost(url); 
	        	StringEntity se = new StringEntity(params.toString());   
	        	request.setEntity(se); 
	        	HttpResponse httpResponse = new DefaultHttpClient().execute(request); 
	        	String resp = EntityUtils.toString(httpResponse.getEntity());
	        	m.obj = resp;
	        	//JSONObject jsonRes = new JSONObject(resp);
	            //m.obj = jsonRes;
	        }  
	        handler.sendMessage(m);
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }  
}  
