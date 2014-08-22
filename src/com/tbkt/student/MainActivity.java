package com.tbkt.student;

import org.json.JSONObject;

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

public class MainActivity extends ActionBarActivity {
	EditText username = null;
	
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
		username = (EditText)findViewById(R.id.username);
		username.setCompoundDrawables(sd.getDrawable(), null, null, null);
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		username.measure(w, h); 
		
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
		//Button btn_login = (Button)findViewById(R.id.btn_login);
		EditText username = (EditText)findViewById(R.id.username);
		EditText passowrd = (EditText)findViewById(R.id.password);
		String str_username = username.getText().toString().trim();
		String str_password = passowrd.getText().toString().trim();
		if(str_username.equals("") || str_password.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(), "请输入账号或密码", Toast.LENGTH_LONG);
			toast.show();
		} else {
			String url = "http://studentapi.tbkt.cn/account/auth/";
			//String params = String.format("username=%s&password=%s", str_username, str_password);
			try{
				JSONObject params = new JSONObject();  
				params.put("username", str_username);  
				params.put("password", str_password);
				new Thread(new HttpJsonUtil("POST", url, params, handler, 100)).start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
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
