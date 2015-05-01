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
	// ������
	private ProgressDialog progressDialog;
	// �����ı���
	private TextView titleView;
	// ListView
	private ListView listView;
	// ArrayAdapter������
	private ArrayAdapter<String> adapter;
	
	private MimosaWeatherDB mimosaWeatherDB;
	private List<String> dataList = new ArrayList<String>();

	private List<Province> provinceList; // ʡ�б�

	private List<City> cityList; // ���б�

	private List<County> countyList; // ���б�

	/**
	 * �û���ǰѡ�е�ʡ���У�����Ϣ��
	 */
	private Province selectedProvince;
	private City selectedCity;

	private int currentLevel; // ��ǰѡ�еļ���

	/**
	 * �˷���˵�������ʱ����Ҫ�������顣 A��ʵ���������ռ�����Ӧ�Ķ��󣬰������ݿ����
	 * B������һ�����͵�ArrayList���飬ͨ���������Ĺ��캯��ʵ����������,�������������ݸ�listView�� C��ע��ListView�ĵ���¼�
	 * D��
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ���ر�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		
		// ʵ�������ݿ���󣬲����������ݿ���Ĺ��췽���Ƿ�װ�����ģ�������Ҫ����������getInstance����������
		mimosaWeatherDB = MimosaWeatherDB.getInstance(this);
		
		/**
		 * ��ȡ���ֿؼ���ʵ��
		 */
		titleView = (TextView) findViewById(R.id.title_text);
		listView = (ListView) findViewById(R.id.list_view);

		// ͨ���������Ĺ��캯��ʵ����������,�������������ݸ�listView��
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);

		

		/**
		 * ע��ListView����¼�
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvince = provinceList.get(position); // ��ѡ�е�ʡ����Ϣ��ֵ��selectedProvince
					queryCities();
				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounty();
				}
			}
		});
		queryProvince(); // �����ʱ��ֱ�Ӳ�ѯʡ����Ϣ
	}

	/**
	 * �˷����ڱ�������ֱ�Ӽ���ȫ����ʡ����Ϣ�� ���ȴ����ݿ��м���ʡ����Ϣ��������ݿ���û�У��ʹӷ������в�ѯ
	 */
	public void queryProvince() {
		provinceList = mimosaWeatherDB.loadProvince();
		if (provinceList.size() > 0) {
			dataList.clear(); // ������ݿ�����province����Ϣ�ͽ������������������ArrayList������գ��Ա����¼���
			// ��ǿforѭ������ȡʡ����Ϣ�����洢��dataList��
			for (Province province : provinceList) {
				dataList.add(province.getProvinceName());
			}

			// ʵ�ָ�ֵ֮���������������notify��������֪ͨ���������и���
			adapter.notifyDataSetChanged();
			listView.setSelection(0); // ���õ�ǰѡ��λ��Ϊ0
			titleView.setText("�й�"); // ���Զ���������������ı���ʾΪ���й���
			currentLevel = LEVEL_PROVINCE; // ���õ�ǰѡ�м���Ϊʡ��
		} else {
			queryFromServer(null, "province"); // ������ݿ��в�ѯ�������ʹӷ������ϲ�ѯ
		}
	}

	/**
	 * 
	 * ��ѯѡ��ʡ���ڵ������У����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ��������ѯ
	 */
	public void queryCities() {
		// ����ѡ�е�ʡ����Ϣ�����ݿ��в�ѯ��ʡ�µĳ�����Ϣ
		cityList = mimosaWeatherDB.loadCities(selectedProvince
				.getProvinceEnName());
		if (cityList.size() > 0) {
			dataList.clear(); // ������ݿ�����city����Ϣ�ͽ������������������ArrayList������գ��Ա����¼���
			// ��ǿforѭ������ȡʡ����Ϣ�����洢��dataList��
			for (City city : cityList) {
				dataList.add(city.getCityName());
			}

			// ʵ�ָ�ֵ֮���������������notify��������֪ͨ���������и���
			adapter.notifyDataSetChanged();
			listView.setSelection(0); // ���õ�ǰѡ��λ��Ϊ0
			titleView.setText(selectedProvince.getProvinceName()); // ���Զ���������������ı���ʾΪѡ�е�ʡ��
			currentLevel = LEVEL_CITY; // ���õ�ǰѡ�м���Ϊ�м�
		} else {
			queryFromServer(selectedProvince.getProvinceEnName(), "city"); // ������ݿ��в�ѯ�������ʹӷ������ϲ�ѯ
		}
	}

	/**
	 * ����ѡ�е��м����ص���Ϣ
	 */
	public void queryCounty() {
		countyList = mimosaWeatherDB.loadCounties(selectedCity.getCityEnName());
		if (countyList.size() > 0) {
			dataList.clear(); // ������ݿ�����County����Ϣ�ͽ������������������ArrayList������գ��Ա����¼���
			// ��ǿforѭ������ȡʡ����Ϣ�����洢��dataList��
			for (County county : countyList) {
				dataList.add(county.getCountyName());
			}

			// ʵ�ָ�ֵ֮���������������notify��������֪ͨ���������и���
			adapter.notifyDataSetChanged();
			listView.setSelection(0); // ���õ�ǰѡ��λ��Ϊ0
			titleView.setText(selectedCity.getCityName()); // ���Զ���������������ı���ʾΪ���й���
			currentLevel = LEVEL_COUNTY; // ���õ�ǰѡ�м���Ϊ�ؼ�
		} else {
			queryFromServer(selectedCity.getCityEnName(), "county"); // ������ݿ��в�ѯ�������ʹӷ������ϲ�ѯ
		}
	}

	/**
	 * ���ݴ���Ĵ��ź����ʹӷ������ϲ�ѯ��Ϣ A:�Ӳ���ȷ����Ҫ��ѯ����ʡ���л����ص����ݡ� B��
	 */
	private void queryFromServer(final String enName, final String type) {
		// TODO Auto-generated method stub

		String address;
		if (!TextUtils.isEmpty(enName)) { // ���ݴ����code�����ж�ʹ��ʲô���ĵ�ַ
			// ���Ϊ���벻Ϊ��ʹ�����µ�ַ
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
					//�����ȷ���ղ�������xml���ݣ���ͨ��runOnUiThread���������ص����̴߳����߼�
					runOnUiThread(new Runnable() {
						public void run() {
							closeProgressDialog();
							//������ѯ��Ӧ�ĳ�����Ϣ
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
						Toast.makeText(ChooseAreaActivity.this, "����ʧ��",Toast.LENGTH_SHORT).show();
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
	
	//����BACK�����ݵ�ǰ�����ж��Ƿ������б�����ֱ���˳���
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
