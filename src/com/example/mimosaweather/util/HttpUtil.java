package com.example.mimosaweather.util;

import java.net.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



/**
 * 链接服务器的工具类
 * 
 * @author xu
 * 
 */
public class HttpUtil {

	/**
	 * 发送请求到服务器的方法
	 */
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			// 开启线程,执行耗时操作
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection=null;
				try {
//					URL url=new URL(address);  //创建地址为address的URL对象
//					connection=(HttpURLConnection) url.openConnection(); //实例化connection
//					connection.setRequestMethod("GET");    //get表示想从服务器那里获取数据
//					connection.setConnectTimeout(8000);    //设置连接超时
//					connection.setReadTimeout(8000);       //设置读取时间超时
//					//获取输入流
//					InputStream inputStream=connection.getInputStream();
//					//封装输入流
//					BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
//					StringBuilder response=new StringBuilder();
//					String line;
//					while((line=reader.readLine())!=null){   //从缓冲流中读取数据存入到response中
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
					if(listener!=null){   //判断接口变量是否为空
						//回调onfinish方法
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					//如果捕获到异常，就回调接口的onError
					if(listener!=null){
						listener.onError(e);
					}
				}finally{
					if(connection!=null){      //最终关闭连接，释放资源
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
