package com.tbkt.student;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	EditText username = null;
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
		System.out.println(username.getMeasuredHeight());
		
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
}
