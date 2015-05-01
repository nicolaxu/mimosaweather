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
 * 封装一些常用的数据库操作，方便以后使用
 * 
 * @author xu
 * 
 */
public class MimosaWeatherDB {

	// 数据库名
	public static final String DB_NAME = "mimosa_weather";

	// 数据库版本
	public static final int VERSION = 1;

	// 创建对象
	private static MimosaWeatherDB mimosaWeatherDB;
	private SQLiteDatabase db;

	// 将构造方法私有化
	private MimosaWeatherDB(Context context) {
		MimosaWeatherOpenHelper helper = new MimosaWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		//实例化数据库对象db
		db = helper.getWritableDatabase(); 
	}

	/**
	 * 获取MimosaWeatherDB的实例,加线程锁
	 */
	public synchronized static MimosaWeatherDB getInstance(Context context) {
		if (mimosaWeatherDB == null) {
			mimosaWeatherDB = new MimosaWeatherDB(context);
		}

		return mimosaWeatherDB;
	}

	/**
	 * 将province实例存储到数据库 。
	 * 
	 * a:创建contentvalues对象，赋值键值对。
	 * 
	 * b:通过db数据库对象存入city表中。
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues(); // 实例化ContentValues，contentvalues只能存储基本类型的数据，不可存储对象，这点没有Hashtable好
			values.put("province_name", province.getProvinceName());
			values.put("province_en_name", province.getProvinceEnName());
			db.insert("Province", null, values);
		}
	}

	/**
	 * 从数据库中读取全国的省份信息。
	 * 
	 * a:获取省份表的游标 。
	 * 
	 * b:从数据表中读取到相应的数据存储到province对象中，并存入list泛型中。
	 * 因为省份信息是只要查询就直接全部加载出来的，不存在哪个国家的哪个省份，所以此方法没有参数
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
	 * 将City实例存储到数据库中 。
	 * 
	 * a:创建ContentValues对象，赋值键值对 。
	 * 
	 * b:通过db数据库对象存入city表中。
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
	 * 从数据库中读取某省下的城市信息。
	 * 
	 * A：创建List泛型City对象。
	 * 
	 * B：获取游标 。
	 * 
	 * C：从数据库中读取某省下的城市信息,并存入对像相应的变量中。
	 */
	public List<City> loadCities(String provinceEnName) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_en_name=?",
				new String[] { String.valueOf(provinceEnName) }, null, null, null);
																			
		if (cursor.moveToFirst()) {
			do {
				// 创建City对象
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
	 * 将County实例存储到数据库中 。
	 * 
	 * a:创建ContentValues对象，赋值键值对 。
	 * 
	 * b:通过db数据库对象存入county表中。
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
	 * 从数据库中读取某市下的城市信息。
	 * 
	 * A：创建List泛型City对象。
	 * 
	 * B：获取游标 。
	 * 
	 * C：从数据库中读取某市下的县的信息,并存入对像相应的变量中。
	 */
	public List<County> loadCounties(String cityEnName) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_en_name=?",
				new String[] { String.valueOf(cityEnName) }, null, null, null);
		if(cursor.moveToFirst()){
			do{
				//创建County对象，用于存储从数据库中读取到的信息
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
