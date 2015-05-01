package com.example.mimosaweather.util;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public interface HttpCallbackListener {

	/**
	 * 此接口负责监听处理服务器返回的结果：
	 * 
	 * A：接收成功
	 * 
	 * B：接收出错
	 * @param response
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	void onFinish(String response) throws XmlPullParserException, IOException;
	void onError(Exception e);
}
