package com.example.mimosaweather.util;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.text.TextUtils;

import com.example.mimosaweather.db.MimosaWeatherDB;
import com.example.mimosaweather.model.City;
import com.example.mimosaweather.model.County;
import com.example.mimosaweather.model.Province;

/**
 * 处理服务器的返回数据的工具类
 * 
 * @author xu
 * 
 */
public class Utility {

	/**
	 * 解析和处理服务器返回的省级数据, 并加线程锁
	 * 
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public synchronized static boolean handleProvinceResponse(
			MimosaWeatherDB mimosaWeatherDB, String response)
			throws XmlPullParserException, IOException {

		if (!TextUtils.isEmpty(response)) { // 判断字符串是否为空
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // 实例化XML解析对象
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response)); // 封装输入流
			int eventType = xmlPullParser.getEventType();
			String quName = "";
			String pyName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) { // 判断是否到了文档末尾
				// 获取节点名字
				String nodeName = xmlPullParser.getName();
				switch (eventType) { // 以节点类型为判定依据
				// 开始解析某个节点
				case XmlPullParser.START_TAG: {
					if ("city".equals(nodeName)) {
						quName = xmlPullParser
								.getAttributeValue(null, "quName"); // 获取键的对应值，记得抛出异常
						pyName = xmlPullParser
								.getAttributeValue(null, "pyName");

						Province province = new Province();
						province.setProvinceName(quName);
						province.setProvinceEnName(pyName);
						mimosaWeatherDB.saveProvince(province);
					}
					break;
				}
				// 完成解析某个节点
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
			return true;
		}

		return false;

	}

	/**
	 * 解析和处理服务器返回的市级数据
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static boolean handleCityResponse(MimosaWeatherDB mimosaWeatherDB,
			String response, String provinceEnName)
			throws XmlPullParserException, IOException {

		if (!TextUtils.isEmpty(response)) { // 判断字符串是否为空
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // 实例化XML解析对象
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response)); // 封装输入流
			int eventType = xmlPullParser.getEventType();
			String cityName = "";
			String cityEnName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) { // 判断是否到了文档末尾
				// 获取节点名字
				String nodeName = xmlPullParser.getName();
				switch (eventType) { // 以节点类型为判定依据
				// 开始解析某个节点
				case XmlPullParser.START_TAG: {
					if ("city".equals(nodeName)) {
						cityName = xmlPullParser.getAttributeValue(null,
								"cityname"); // 获取键的对应值，记得抛出异常
						cityEnName = xmlPullParser.getAttributeValue(null,
								"pyName");

						City city = new City();
						city.setCityName(cityName);
						city.setCityEnName(cityEnName);
						city.setProvinceEnName(provinceEnName);
						mimosaWeatherDB.saveCity(city);
					}
					break;
				}
				// 完成解析某个节点
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
			return true;
		}

		return false;

	}

	/**
	 * 解析和处理服务器返回的县的信息
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static boolean handleCountyResponse(MimosaWeatherDB mimosaWeatherDB,
			String response, String cityEnName) throws XmlPullParserException,
			IOException {

		if (!TextUtils.isEmpty(response)) { // 判断字符串是否为空
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // 实例化XML解析对象
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response)); // 封装输入流
			int eventType = xmlPullParser.getEventType();
			String cityName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) { // 判断是否到了文档末尾
				// 获取节点名字
				String nodeName = xmlPullParser.getName();
				switch (eventType) { // 以节点类型为判定依据
				// 开始解析某个节点
				case XmlPullParser.START_TAG: {
					if ("city".equals(nodeName)) {
						cityName = xmlPullParser.getAttributeValue(null,
								"cityname"); // 获取键的对应值，记得抛出异常
						County county = new County();
						county.setCountyName(cityName);
						county.setCityEnName(cityEnName);
						mimosaWeatherDB.saveCounty(county);
					}
					break;
				}
				// 完成解析某个节点
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
			return true;
		}

		return false;

	}
}
