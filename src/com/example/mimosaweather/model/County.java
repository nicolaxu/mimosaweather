package com.example.mimosaweather.model;
/**
 * 此处的county类与数据中的county表对应
 * @author xu
 *
 */
public class County {

	private int id;
	private String countyName;
	private String cityEnName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCityEnName() {
		return cityEnName;
	}
	public void setCityEnName(String cityEnName) {
		this.cityEnName = cityEnName;
	}
	
	
	
}
