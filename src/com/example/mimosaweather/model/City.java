package com.example.mimosaweather.model;

/**
 * �˴���city�������ݿ��е�city���Ӧ
 * @author xu
 *
 */
public class City {
	private int id;   //���е�id�ֶ�
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
