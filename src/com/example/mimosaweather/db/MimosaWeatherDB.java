package com.example.mimosaweather.db;

import java.util.ArrayList;
import java.util.List;

import com.example.mimosaweather.model.City;
import com.example.mimosaweather.model.County;
import com.example.mimosaweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��װһЩ���õ����ݿ�����������Ժ�ʹ��
 * 
 * @author xu
 * 
 */
public class MimosaWeatherDB {

	// ���ݿ���
	public static final String DB_NAME = "mimosa_weather";

	// ���ݿ�汾
	public static final int VERSION = 1;

	// ��������
	private static MimosaWeatherDB mimosaWeatherDB;
	private SQLiteDatabase db;

	// �����췽��˽�л�
	private MimosaWeatherDB(Context context) {
		MimosaWeatherOpenHelper helper = new MimosaWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = helper.getWritableDatabase();
	}

	/**
	 * ��ȡMimosaWeatherDB��ʵ��,���߳���
	 */
	public synchronized static MimosaWeatherDB getInstance(Context context) {
		if (mimosaWeatherDB == null) {
			mimosaWeatherDB = new MimosaWeatherDB(context);
		}

		return mimosaWeatherDB;
	}

	/**
	 * ��provinceʵ���洢�����ݿ� ��
	 * 
	 * a:����contentvalues���󣬸�ֵ��ֵ�ԡ�
	 * 
	 * b:ͨ��db���ݿ�������city���С�
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues(); // ʵ����ContentValues��contentvaluesֻ�ܴ洢�������͵����ݣ����ɴ洢�������û��Hashtable��
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * �����ݿ��ж�ȡȫ����ʡ����Ϣ��
	 * 
	 * a:��ȡʡ�ݱ���α� ��
	 * 
	 * b:�����ݱ��ж�ȡ����Ӧ�����ݴ洢��province�����У�������list�����С�
	 */
	public List<Province> loadProvince() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * ��Cityʵ���洢�����ݿ��� ��
	 * 
	 * a:����ContentValues���󣬸�ֵ��ֵ�� ��
	 * 
	 * b:ͨ��db���ݿ�������city���С�
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/**
	 * �����ݿ��ж�ȡĳʡ�µĳ�����Ϣ��
	 * 
	 * A������List����City����
	 * 
	 * B����ȡ�α� ��
	 * 
	 * C�������ݿ��ж�ȡĳʡ�µĳ�����Ϣ,�����������Ӧ�ı����С�
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id=?",
				new String[] { String.valueOf(provinceId) }, null, null, null);// String[]
																				// selectionArgs
		if (cursor.moveToFirst()) {
			do {
				// ����City����
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * �����ݿ��ж�ȡĳ���µĳ�����Ϣ��
	 * 
	 * A������List����City����
	 * 
	 * B����ȡ�α� ��
	 * 
	 * C�������ݿ��ж�ȡĳ���µ��ص���Ϣ,�����������Ӧ�ı����С�
	 */
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id=?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if(cursor.moveToFirst()){
			do{
				//����County�������ڴ洢�����ݿ��ж�ȡ������Ϣ
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("countyId")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("countyCode")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
