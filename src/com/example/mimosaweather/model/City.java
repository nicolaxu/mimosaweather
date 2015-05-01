package com.example.mimosaweather.model;

/**
 * 此处的city类与数据库中的city表对应
 * @author xu
 *
 */
public class City {
	private int id;   //表中的id字段
	private String cityName;
	private String cityEnName;
	private String provinceEnName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityEnName() {
		return cityEnName;
	}
	public void setCityEnName(String cityEnName) {
		this.cityEnName = cityEnName;
	}
	public String getProvinceEnName() {
		return provinceEnName;
	}
	public void setProvinceEnName(String provinceName) {
		this.provinceEnName = provinceName;
	}
	
}
