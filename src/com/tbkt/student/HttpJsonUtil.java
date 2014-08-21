package com.tbkt.student;

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
    private String params;  
    private Handler handler;
    private int what;
      
    public HttpJsonUtil(String method, String url, String params, Handler handler, int what) {  
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
	            m.obj = GetPostUtil.sendGet(url, params);  
	            //JSONObject jsonRes = new JSONObject(result);  // 解析sjon格式数据
	            //JSONObject jsonData = jsonRes.getJSONObject("data");
	        }  
	        if(method.equals("POST")){  
	            //Log.i("iiiiiii","发送POST请求");  
	        	m.obj = GetPostUtil.sendPost(url, params);  
	        	//String resp = GetPostUtil.sendPost(url, params);
	        	//JSONObject jsonRes = new JSONObject(resp);
	            //m.obj = jsonRes;
	        }  
	        handler.sendMessage(m);
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }  
}  
