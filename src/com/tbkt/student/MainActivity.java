package com.tbkt.student;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbkt.student.util.DialogUtil;
import com.tbkt.student.util.HttpUtil;

public class MainActivity extends ActionBarActivity {
	// 定义界面中两个文本框
	EditText etUsername, etPassword;
	Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 100) {
            	try{
            		JSONObject jsonRes = new JSONObject(msg.obj.toString());
            		if(jsonRes.getBoolean("success")) {
            			Toast toast = Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG);
            			toast.show();
            		} else {
            			System.out.println("fail ");
            			String errors = jsonRes.getString("errors");
            			System.out.println(errors);
            			Toast toast = Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_LONG);
            			toast.show();
            		}
            	} catch(Exception e){
            		e.printStackTrace();
            	}
            }
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 设置用户名输入框图标大小
		Drawable drawable = getResources().getDrawable(R.drawable.icon_username);
		drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5), (int)(drawable.getIntrinsicHeight()*0.5));
		ScaleDrawable sd = new ScaleDrawable(drawable, 0, 100, 100);
		etUsername = (EditText)findViewById(R.id.username);
		etPassword = (EditText)findViewById(R.id.password);
		etUsername.setCompoundDrawables(sd.getDrawable(), null, null, null);
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		etUsername.measure(w, h); 
		
		// Remove underlines from HTML links
	    TextView tbkt_link = (TextView)findViewById(R.id.tbkt_link);
	    // Make sure the TextView was instantiated correctly
	    if(tbkt_link != null) {
	        removeUnderlines((Spannable)tbkt_link.getText());
	    }
	    // Remove underlines from HTML links
	    TextView phone_link = (TextView)findViewById(R.id.phone_link);
	    // Make sure the TextView was instantiated correctly
	    if(phone_link != null) {
	    	removeUnderlines((Spannable)phone_link.getText());
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void clickLogin(View source){
		// 单击登录事件
		// 执行输入校验
		if (validate()) {
			// 如果登录成功
			if (loginPro()) {
				// 启动Main Activity
				Intent intent = new Intent(MainActivity.this, AuctionClientActivity.class);
				startActivity(intent);
				// 结束该Activity
				finish();
			}
			else {
				DialogUtil.showDialog(MainActivity.this, "用户名称或者密码错误，请重新输入！", false);
			}
		}
	}
	
	// 对用户输入的用户名、密码进行校验
	private boolean validate() {
		String username = etUsername.getText().toString().trim();
		if (username.equals("")) {
			DialogUtil.showDialog(this, "用户账户不能为空！", false);
			return false;
		}
		String pwd = etPassword.getText().toString().trim();
		if (pwd.equals("")) {
			DialogUtil.showDialog(this, "用户密码不能为空！", false);
			return false;
		}
		return true;
	}
	
	private boolean loginPro() {
		// 获取用户输入的用户名、密码
		String str_username = etUsername.getText().toString();
		String pwd = etPassword.getText().toString();
		JSONObject jsonObj;
		try {
			jsonObj = query(str_username, pwd);
			// 如果userId 大于0
			if (jsonObj.getBoolean("success")) {
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
		}

		return false;
	}
	
	// 定义发送请求的方法
	private JSONObject query(String username, String password) throws Exception {
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("pasword", password);
		// 定义发送请求的URL
		String url = HttpUtil.BASE_URL + "/account/auth/";
		// 发送请求
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	
	public static Spannable removeUnderlines(Spannable p_Text) {  
		// 移除下划线
        URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);  
        for (URLSpan span : spans) {  
             int start = p_Text.getSpanStart(span);  
             int end = p_Text.getSpanEnd(span);  
             p_Text.removeSpan(span);  
             span = new URLSpanNoUnderline(span.getURL());  
             p_Text.setSpan(span, start, end, 0);  
        }  
        return p_Text;  
   }  
	
}
