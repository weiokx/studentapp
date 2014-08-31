package com.tbkt.student;

import org.json.JSONObject;

import android.app.ActionBar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class HomeActivity extends ActionBarActivity {
	MyDatabaseHelper dbHelper;
	EditText username = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		TextView tv_sessionid = (TextView)findViewById(R.id.ssssionid);
		final ActionBar bar = getActionBar();
		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_TITLE); // 设置标题栏不显示HOME图标与标题
		
		try{
			dbHelper = new MyDatabaseHelper(this, "tbkt.db3", 1);
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from user where name='sessionid'", null);
//			while(cursor.moveToNext()){ 
//				int nameColumnIndex = cursor.getColumnIndex("value");
//				String sessionid = cursor.getString(nameColumnIndex); 
//				tv_sessionid.setText(sessionid);
//			}
			if(cursor.getCount() >= 1) {
				cursor.moveToFirst();
				int nameColumnIndex = cursor.getColumnIndex("value");
				String sessionid = cursor.getString(nameColumnIndex); 
				tv_sessionid.setText(sessionid);
			}
				
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

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
	
}
