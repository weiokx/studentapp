package com.tbkt.student;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbkt.student.util.HttpUtil;

public class MainActivity extends ActionBarActivity {
	MyDatabaseHelper dbHelper;
	// ��������������ı���
	EditText etUsername, etPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// �����û��������ͼ���С
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
		// ������¼�¼�
		// ִ������У��
		if (validate()) {
			// �����¼�ɹ�
			if (loginPro()) {
				// ����Main Activity
				Intent intent = new Intent(MainActivity.this, HomeActivity.class);
				startActivity(intent);
				// ������Activity
				finish();
			} 
		}
	}
	
	// ���û�������û������������У��
	private boolean validate() {
		String username = etUsername.getText().toString().trim();
		if (username.equals("")) {
			Toast.makeText(getApplicationContext(), "�û��˻�����Ϊ�գ�", Toast.LENGTH_LONG).show();
			return false;
		}
		String pwd = etPassword.getText().toString().trim();
		if (pwd.equals("")) {
			Toast.makeText(getApplicationContext(), "�û����벻��Ϊ�գ�", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	private boolean loginPro() {
		// ��ȡ�û�������û���������
		String str_username = etUsername.getText().toString();
		String str_password = etPassword.getText().toString();
		JSONObject jsonRes;
		try {
			JSONObject params = new JSONObject();  
			params.put("username", str_username);  
			params.put("password", str_password);
			// ���巢�������URL
			String url = HttpUtil.BASE_URL + "/account/auth/";
			// ��������
			jsonRes = new JSONObject(HttpUtil.postRequest(url, params));
			if (jsonRes.getBoolean("success")) {
				JSONObject jData = jsonRes.getJSONObject("data");
				String sessionid = jData.getString("sessionid");
				//db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+ "/my.db3", null); // �򿪻򴴽����ݿ�
				dbHelper = new MyDatabaseHelper(this, "tbkt.db3", 1);
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor cursor = db.rawQuery("select * from user where name=\"sessionid\"", null);
				if(cursor.moveToFirst() == true) { // ���ڼ�¼���������
					db.execSQL("update user set value=? where name=\"sessionid\"", new String[] {sessionid});
				} else { // ���һ������
					db.execSQL("insert into user values(null , ? , ?)", new String[] {"sessionid", sessionid});
				}
				return true;
			} else {
				Toast.makeText(getApplicationContext(), jsonRes.getString("errors"), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "��������Ӧ�쳣�����Ժ����ԣ�", Toast.LENGTH_LONG).show();
			System.out.println(e);
			e.printStackTrace();
		}

		return false;
	}
	
	public static Spannable removeUnderlines(Spannable p_Text) {  
		// �Ƴ��»���
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		// �˳�����ʱ�ر�SQLiteDatabase
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
	
}
