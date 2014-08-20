package com.tbkt.student;

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
        
    }  
  
    @Override  
    public void run() {  
        Message m = new Message();  
        m.what = what;  
        if(method.equals("GET")){  
            Log.i("iiiiiii","发送GET请求");  
            m.obj = GetPostUtil.sendGet(url, params);  
            Log.i("iiiiiii",">>>>>>>>>>>>"+m.obj);  
        }  
        if(method.equals("POST")){  
            Log.i("iiiiiii","发送POST请求");  
            m.obj = GetPostUtil.sendPost(url, params);  
            Log.i("gggggggg",">>>>>>>>>>>>"+m.obj);  
        }  
        handler.sendMessage(m);  
    }  
}  
