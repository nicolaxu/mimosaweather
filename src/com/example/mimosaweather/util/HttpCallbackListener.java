package com.example.mimosaweather.util;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

public interface HttpCallbackListener {

	/**
	 * �˽ӿڸ������������������صĽ����
	 * 
	 * A�����ճɹ�
	 * 
	 * B�����ճ���
	 * @param response
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	void onFinish(String response) throws XmlPullParserException, IOException;
	void onError(Exception e);
}
