package com.example.mimosaweather.util;

import java.net.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



/**
 * ���ӷ������Ĺ�����
 * 
 * @author xu
 * 
 */
public class HttpUtil {

	/**
	 * �������󵽷������ķ���
	 */
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			// �����߳�,ִ�к�ʱ����
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection=null;
				try {
//					URL url=new URL(address);  //������ַΪaddress��URL����
//					connection=(HttpURLConnection) url.openConnection(); //ʵ����connection
//					connection.setRequestMethod("GET");    //get��ʾ��ӷ����������ȡ����
//					connection.setConnectTimeout(8000);    //�������ӳ�ʱ
//					connection.setReadTimeout(8000);       //���ö�ȡʱ�䳬ʱ
//					//��ȡ������
//					InputStream inputStream=connection.getInputStream();
//					//��װ������
//					BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
//					StringBuilder response=new StringBuilder();
//					String line;
//					while((line=reader.readLine())!=null){   //�ӻ������ж�ȡ���ݴ��뵽response��
//						response.append(line);
//					}
					String response="";
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(address);
					HttpResponse httpResponse=httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						HttpEntity entity=httpResponse.getEntity();
						response=EntityUtils.toString(entity,"utf-8");
					}
					if(listener!=null){   //�жϽӿڱ����Ƿ�Ϊ��
						//�ص�onfinish����
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					//��������쳣���ͻص��ӿڵ�onError
					if(listener!=null){
						listener.onError(e);
					}
				}finally{
					if(connection!=null){      //���չر����ӣ��ͷ���Դ
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
