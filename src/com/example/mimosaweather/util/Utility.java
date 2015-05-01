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
 * ����������ķ������ݵĹ�����
 * 
 * @author xu
 * 
 */
public class Utility {

	/**
	 * �����ʹ�����������ص�ʡ������, �����߳���
	 * 
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public synchronized static boolean handleProvinceResponse(
			MimosaWeatherDB mimosaWeatherDB, String response)
			throws XmlPullParserException, IOException {

		if (!TextUtils.isEmpty(response)) { // �ж��ַ����Ƿ�Ϊ��
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // ʵ����XML��������
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response)); // ��װ������
			int eventType = xmlPullParser.getEventType();
			String quName = "";
			String pyName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) { // �ж��Ƿ����ĵ�ĩβ
				// ��ȡ�ڵ�����
				String nodeName = xmlPullParser.getName();
				switch (eventType) { // �Խڵ�����Ϊ�ж�����
				// ��ʼ����ĳ���ڵ�
				case XmlPullParser.START_TAG: {
					if ("city".equals(nodeName)) {
						quName = xmlPullParser
								.getAttributeValue(null, "quName"); // ��ȡ���Ķ�Ӧֵ���ǵ��׳��쳣
						pyName = xmlPullParser
								.getAttributeValue(null, "pyName");

						Province province = new Province();
						province.setProvinceName(quName);
						province.setProvinceEnName(pyName);
						mimosaWeatherDB.saveProvince(province);
					}
					break;
				}
				// ��ɽ���ĳ���ڵ�
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
	 * �����ʹ�����������ص��м�����
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static boolean handleCityResponse(MimosaWeatherDB mimosaWeatherDB,
			String response, String provinceEnName)
			throws XmlPullParserException, IOException {

		if (!TextUtils.isEmpty(response)) { // �ж��ַ����Ƿ�Ϊ��
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // ʵ����XML��������
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response)); // ��װ������
			int eventType = xmlPullParser.getEventType();
			String cityName = "";
			String cityEnName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) { // �ж��Ƿ����ĵ�ĩβ
				// ��ȡ�ڵ�����
				String nodeName = xmlPullParser.getName();
				switch (eventType) { // �Խڵ�����Ϊ�ж�����
				// ��ʼ����ĳ���ڵ�
				case XmlPullParser.START_TAG: {
					if ("city".equals(nodeName)) {
						cityName = xmlPullParser.getAttributeValue(null,
								"cityname"); // ��ȡ���Ķ�Ӧֵ���ǵ��׳��쳣
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
				// ��ɽ���ĳ���ڵ�
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
	 * �����ʹ�����������ص��ص���Ϣ
	 * 
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static boolean handleCountyResponse(MimosaWeatherDB mimosaWeatherDB,
			String response, String cityEnName) throws XmlPullParserException,
			IOException {

		if (!TextUtils.isEmpty(response)) { // �ж��ַ����Ƿ�Ϊ��
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // ʵ����XML��������
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response)); // ��װ������
			int eventType = xmlPullParser.getEventType();
			String cityName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) { // �ж��Ƿ����ĵ�ĩβ
				// ��ȡ�ڵ�����
				String nodeName = xmlPullParser.getName();
				switch (eventType) { // �Խڵ�����Ϊ�ж�����
				// ��ʼ����ĳ���ڵ�
				case XmlPullParser.START_TAG: {
					if ("city".equals(nodeName)) {
						cityName = xmlPullParser.getAttributeValue(null,
								"cityname"); // ��ȡ���Ķ�Ӧֵ���ǵ��׳��쳣
						County county = new County();
						county.setCountyName(cityName);
						county.setCityEnName(cityEnName);
						mimosaWeatherDB.saveCounty(county);
					}
					break;
				}
				// ��ɽ���ĳ���ڵ�
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
