package com.example.mimosaweather.model;

/**
 * 此处的province类与数据库中的province表所对应
 * 
 * @author xu
 * 
 */

public class Province {

	private int id; // 存储省表的id字段
	private String provinceName;
	private String provinceCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	

}
