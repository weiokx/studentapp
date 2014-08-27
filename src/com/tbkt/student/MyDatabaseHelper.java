/**
 *
 */
package com.tbkt.student;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
	final String CREATE_TABLE_SQL = "create table user(_id integer primary key autoincrement, name, value)";
	public MyDatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// ����ʹ�����ݿ�ʱ, ������ݿⲻ���ڣ����Զ�������������onCreate������������Ŀ���������һ���԰���Ŀ����Ҫ�ı�ȫ�����ˣ�
		db.execSQL(CREATE_TABLE_SQL);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("--------onUpdate Called--------" + oldVersion + "--->" + newVersion);
	}
}
