package com.example.mimosaweather.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.example.mimosaweather.R;
import com.example.mimosaweather.db.MimosaWeatherDB;
import com.example.mimosaweather.model.City;
import com.example.mimosaweather.model.County;
import com.example.mimosaweather.model.Province;
import com.example.mimosaweather.util.HttpCallbackListener;
import com.example.mimosaweather.util.HttpUtil;
import com.example.mimosaweather.util.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends Activity {

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	// 进度条
	private ProgressDialog progressDialog;
	// 标题文本框
	private TextView titleView;
	// ListView
	private ListView listView;
	// ArrayAdapter适配器
	private ArrayAdapter<String> adapter;
	
	private MimosaWeatherDB mimosaWeatherDB;
	private List<String> dataList = new ArrayList<String>();

	private List<Province> provinceList; // 省列表

	private List<City> cityList; // 市列表

	private List<County> countyList; // 县列表

	/**
	 * 用户当前选中的省，市，县信息。
	 */
	private Province selectedProvince;
	private City selectedCity;

	private int currentLevel; // 当前选中的级别

	/**
	 * 此方法说明活动加载时所需要做的事情。 A：实例化各个空间所对应的对象，包括数据库对象。
	 * B：根据一个泛型的ArrayList数组，通过适配器的构造函数实例化适配器,并将适配器传递给listView。 C：注册ListView的点击事件
	 * D：
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		
		// 实例化数据库对象，并且由于数据库类的构造方法是分装起来的，所以需要用类名调用getInstance（）方法。
		mimosaWeatherDB = MimosaWeatherDB.getInstance(this);
		
		/**
		 * 获取各种控件的实例
		 */
		titleView = (TextView) findViewById(R.id.title_text);
		listView = (ListView) findViewById(R.id.list_view);

		// 通过适配器的构造函数实例化适配器,并将适配器传递给listView。
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);

		

		/**
		 * 注册ListView点击事件
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvince = provinceList.get(position); // 将选中的省份信息赋值给selectedProvince
					queryCities();
				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounty();
				}
			}
		});
		queryProvince(); // 活动加载时就直接查询省份信息
	}

	/**
	 * 此方法在被调用是直接加载全国的省份信息。 首先从数据库中加载省份信息，如果数据库中没有，就从服务器中查询
	 */
	public void queryProvince() {
		provinceList = mimosaWeatherDB.loadProvince();
		if (provinceList.size() > 0) {
			dataList.clear(); // 如果数据库中有province的信息就将构建适配器所必须的ArrayList数组清空，以便重新加载
			// 增强for循环，获取省份信息，并存储到dataList中
			for (Province province : provinceList) {
				dataList.add(province.getProvinceName());
			}

			// 实现赋值之后，需调用适配器的notify方法，以通知适配器进行更新
			adapter.notifyDataSetChanged();
			listView.setSelection(0); // 设置当前选中位置为0
			titleView.setText("中国"); // 在自定义标题栏中设置文本显示为“中国”
			currentLevel = LEVEL_PROVINCE; // 设置当前选中级别为省级
		} else {
			queryFromServer(null, "province"); // 如果数据库中查询不到，就从服务器上查询
		}
	}

	/**
	 * 
	 * 查询选中省份内的所有市，有先从数据库查询，如果没有查询到在去服务器查询
	 */
	public void queryCities() {
		// 根据选中的省份信息到数据库中查询该省下的城市信息
		cityList = mimosaWeatherDB.loadCities(selectedProvince
				.getProvinceEnName());
		if (cityList.size() > 0) {
			dataList.clear(); // 如果数据库中有city的信息就将构建适配器所必须的ArrayList数组清空，以便重新加载
			// 增强for循环，获取省份信息，并存储到dataList中
			for (City city : cityList) {
				dataList.add(city.getCityName());
			}

			// 实现赋值之后，需调用适配器的notify方法，以通知适配器进行更新
			adapter.notifyDataSetChanged();
			listView.setSelection(0); // 设置当前选中位置为0
			titleView.setText(selectedProvince.getProvinceName()); // 在自定义标题栏中设置文本显示为选中的省份
			currentLevel = LEVEL_CITY; // 设置当前选中级别为市级
		} else {
			queryFromServer(selectedProvince.getProvinceEnName(), "city"); // 如果数据库中查询不到，就从服务器上查询
		}
	}

	/**
	 * 根据选中的市加载县的信息
	 */
	public void queryCounty() {
		countyList = mimosaWeatherDB.loadCounties(selectedCity.getCityEnName());
		if (countyList.size() > 0) {
			dataList.clear(); // 如果数据库中有County的信息就将构建适配器所必须的ArrayList数组清空，以便重新加载
			// 增强for循环，获取省份信息，并存储到dataList中
			for (County county : countyList) {
				dataList.add(county.getCountyName());
			}

			// 实现赋值之后，需调用适配器的notify方法，以通知适配器进行更新
			adapter.notifyDataSetChanged();
			listView.setSelection(0); // 设置当前选中位置为0
			titleView.setText(selectedCity.getCityName()); // 在自定义标题栏中设置文本显示为“中国”
			currentLevel = LEVEL_COUNTY; // 设置当前选中级别为县级
		} else {
			queryFromServer(selectedCity.getCityEnName(), "county"); // 如果数据库中查询不到，就从服务器上查询
		}
	}

	/**
	 * 根据传入的代号和类型从服务器上查询信息 A:从参数确定所要查询的是省、市还是县的数据。 B：
	 */
	private void queryFromServer(final String enName, final String type) {
		// TODO Auto-generated method stub

		String address;
		if (!TextUtils.isEmpty(enName)) { // 根据传入的code代号判断使用什么样的地址
			// 如果为代码不为空使用如下地址
			address = "http://flash.weather.com.cn/wmaps/xml/" + enName
					+ ".xml";
		} else {
			address = "http://flash.weather.com.cn/wmaps/xml/china.xml";
		}
		showProgressDialog();
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response)
					throws XmlPullParserException, IOException {
				// TODO Auto-generated method stub
				boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvinceResponse(mimosaWeatherDB,
							response);
				} else if ("city".equals(type)) {
					result = Utility.handleCityResponse(mimosaWeatherDB,
							response, selectedProvince.getProvinceEnName());
				}else if("county".equals(type)){
					result=Utility.handleCountyResponse(mimosaWeatherDB, response, selectedCity.getCityEnName());
				}
				if(result){
					//如果正确接收并解析了xml数据，就通过runOnUiThread（）方法回到主线程处理逻辑
					runOnUiThread(new Runnable() {
						public void run() {
							closeProgressDialog();
							//继续查询相应的城镇信息
							if("province".equals(type)){
								queryProvince();
							}else if("city".equals(type)){
								queryCities();
							}else if("county".equals(type)){
								queryCounty();
							}
						}
					});
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "加载失败",Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

	}

	private void showProgressDialog() {
		// TODO Auto-generated method stub
		if(progressDialog==null){
			progressDialog=new ProgressDialog(ChooseAreaActivity.this);
			progressDialog.setMessage("loading...");
			progressDialog.setCancelable(true);
		}
		progressDialog.show();
	}
	
	private void closeProgressDialog(){
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
	}
	
	//捕获BACK键根据当前级别判断是返回市列表，还是直接退出。
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(currentLevel==LEVEL_COUNTY){
			queryCities();
		}else if(currentLevel==LEVEL_CITY){
			queryProvince();
		}else{
			finish();
		}
	}
}
