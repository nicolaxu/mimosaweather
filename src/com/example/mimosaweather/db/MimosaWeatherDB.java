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
		//ʵ�������ݿ����db
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
			values.put("province_en_name", province.getProvinceEnName());
			db.insert("Province", null, values);
		}
	}

	/**
	 * �����ݿ��ж�ȡȫ����ʡ����Ϣ��
	 * 
	 * a:��ȡʡ�ݱ���α� ��
	 * 
	 * b:�����ݱ��ж�ȡ����Ӧ�����ݴ洢��province�����У�������list�����С�
	 * ��Ϊʡ����Ϣ��ֻҪ��ѯ��ֱ��ȫ�����س����ģ��������ĸ����ҵ��ĸ�ʡ�ݣ����Դ˷���û�в���
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
				province.setProvinceEnName(cursor.getString(cursor
						.getColumnIndex("province_en_name")));
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
			values.put("city_en_name", city.getCityEnName());
			values.put("province_en_name", city.getProvinceEnName());
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
	public List<City> loadCities(String provinceEnName) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_en_name=?",
				new String[] { String.valueOf(provinceEnName) }, null, null, null);
																			
		if (cursor.moveToFirst()) {
			do {
				// ����City����
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityEnName(cursor.getString(cursor
						.getColumnIndex("city_en_name")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setProvinceEnName(provinceEnName);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	/**
	 * ��Countyʵ���洢�����ݿ��� ��
	 * 
	 * a:����ContentValues���󣬸�ֵ��ֵ�� ��
	 * 
	 * b:ͨ��db���ݿ�������county���С�
	 */
	public void saveCounty(County county){
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("city_en_name", county.getCityEnName());
			db.insert("County", null, values);
		}
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
	public List<County> loadCounties(String cityEnName) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_en_name=?",
				new String[] { String.valueOf(cityEnName) }, null, null, null);
		if(cursor.moveToFirst()){
			do{
				//����County�������ڴ洢�����ݿ��ж�ȡ������Ϣ
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCityEnName(cityEnName);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
