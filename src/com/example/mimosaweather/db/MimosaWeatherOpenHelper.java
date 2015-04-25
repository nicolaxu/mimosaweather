package com.example.mimosaweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MimosaWeatherOpenHelper extends SQLiteOpenHelper {

	/**
	 *province (ʡ)��Ľ���sql���
	 */
	public static final String CREAT_PROVINCE_TABLE="create table Province ("
			+"id integer primary key autoincrement,"
			+"province_name text,"
			+"province_code text)";
	
	/**
	 *city (��) ��Ľ���sql���
	 */
	public static final String CREATE_CITY_TABLE="create table City ("
			+"id integer primary key autoincrement,"
			+"city_name text,"
			+"city_code text,"
			+"province_id integer)";
	
	/**
	 * county(��)��Ľ���sql���
	 */
	public static final String CREATE_COUNTY="create table County("
			+"id integer primary key autoincrement,"
			+"county_name text,"
			+"county_code text)";
	
	
	//���캯��
	public MimosaWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREAT_PROVINCE_TABLE); //����ʡ��
		db.execSQL(CREATE_CITY_TABLE);    //�����б�
		db.execSQL(CREATE_COUNTY);        //�����ر�
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
