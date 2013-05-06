package com.pms.weather;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pms.bean.WeatherCurrentCondition;
import com.pms.bean.WeatherForecastCondition;
import com.pms.bean.WeatherForecastInformation;
import com.pms.xml.WeatherXmlService;

public class MainActivity extends Activity implements OnClickListener {
	private static final String weatherPath = "http://www.google.com/ig/api?hl=zh_cn&weather=";
	private static final String googlePath = "http://www.google.com";

	EditText et_city = null;// 输入城市
	Button bt_city = null;// 确定查询输入城市天气预报
	// 当前天气情况
	ImageView img_info1 = null;// 天气情况图标
	TextView tv_info1 = null;// 天气情况相关信息(温度、天气、湿度等)
	// 未来3天天气情况
	ImageView img_info2 = null;
	TextView tv_info2 = null;
	ImageView img_info3 = null;
	TextView tv_info3 = null;
	ImageView img_info4 = null;
	TextView tv_info4 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.initialize();
		bt_city.setOnClickListener(this);
	}
	//加载布局文件中部分控件
	private void initialize() {
		et_city = (EditText) this.findViewById(R.id.et_city);
		bt_city = (Button) this.findViewById(R.id.bt_city);
		img_info1 = (ImageView) this.findViewById(R.id.img_info1);
		tv_info1 = (TextView) this.findViewById(R.id.tv_info1);
		img_info2 = (ImageView) this.findViewById(R.id.img_info2);
		tv_info2 = (TextView) this.findViewById(R.id.tv_info2);
		img_info3 = (ImageView) this.findViewById(R.id.img_info3);
		tv_info3 = (TextView) this.findViewById(R.id.tv_info3);
		img_info4 = (ImageView) this.findViewById(R.id.img_info4);
		tv_info4 = (TextView) this.findViewById(R.id.tv_info4);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_city:// 点击确定查询输入城市天气预报
			try {
				String et_city_value = et_city.getText().toString();
				et_city_value = et_city_value == null ? "" : et_city_value;
				if (et_city_value.equals(""))
					et_city_value = "beijing";
				String queryString = weatherPath + et_city_value;
				List<Object> objs = WeatherXmlService
						.getWeatherInfos(queryString);
				if (objs != null && objs.size() == 3) {
					// 按照getWeatherInfos中三个实体对象的写入顺序取出
					WeatherForecastInformation forecastInformation = (WeatherForecastInformation) objs
							.get(0);// 当前预测城市相关信息(GPS、省份、当前时间等)
					WeatherCurrentCondition currentCondition = (WeatherCurrentCondition) objs
							.get(1);// 当前天气情况
					WeatherForecastCondition today = null;// 今天的预报情况
					List<WeatherForecastCondition> forecastConditionList = (List<WeatherForecastCondition>) objs
							.get(2); // 未来几天的天气列表
					if (forecastConditionList != null
							&& forecastConditionList.size() > 0) {
						today = forecastConditionList.get(0);
					}
					MainActivity.this.setTitle(et_city_value + "的天气情况");
					img_info1.setImageBitmap(getBitmapByPath(googlePath
							+ currentCondition.getIconURL()));
					StringBuffer sb = new StringBuffer();
					sb.append("城市： " + forecastInformation.getCity()
							+ "\n预测时间："
							+ forecastInformation.getForecast_date()
							+ "\n当前天气：" + currentCondition.getCondition()
							+ "\n当前湿度：" + currentCondition.getHumidity()
							+ "\n当前温度：" + currentCondition.getTempCelcius()
							+ "℃\n" + currentCondition.getWindCondition());
					if (today != null) {
						sb.append("\n星期：" + today.getDayofWeek());
						sb.append("\n温度最低：" + today.getTempMinCelsius()
								+ "℃  最高：" + today.getTempMaxCelsius() + "℃");
					}
					tv_info1.setText(sb.toString());
					for (int i = 0; i < forecastConditionList.size(); i++) {
						WeatherForecastCondition tmp=forecastConditionList.get(i);
						switch (i) {
						case 0:
							//第一个未来天气是今天，在上面已经显示，所以这里不用操作。
							break;
						case 1:
							img_info2.setImageBitmap(getBitmapByPath(googlePath
									+ tmp.getIconURL()));
							sb = new StringBuffer();
							sb.append("星期:"+tmp.getDayofWeek()+"\n");
							sb.append("天气:"+tmp.getCondition()+"\n");
							sb.append("温度最低：" + tmp.getTempMinCelsius()
									+ "℃  最高：" + tmp.getTempMaxCelsius() + "℃");
							tv_info2.setText(sb.toString());
							break;
						case 2:
							img_info3.setImageBitmap(getBitmapByPath(googlePath
									+ tmp.getIconURL()));
							sb = new StringBuffer();
							sb.append("星期:"+tmp.getDayofWeek()+"\n");
							sb.append("天气:"+tmp.getCondition()+"\n");
							sb.append("温度最低：" + tmp.getTempMinCelsius()
									+ "℃  最高：" + tmp.getTempMaxCelsius() + "℃");
							tv_info3.setText(sb.toString());
							break;
						case 3:
							img_info4.setImageBitmap(getBitmapByPath(googlePath
									+ tmp.getIconURL()));
							sb = new StringBuffer();
							sb.append("星期:"+tmp.getDayofWeek()+"\n");
							sb.append("天气:"+tmp.getCondition()+"\n");
							sb.append("温度最低：" + tmp.getTempMinCelsius()
									+ "℃  最高：" + tmp.getTempMaxCelsius() + "℃");
							tv_info4.setText(sb.toString());
							break;
						default:
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		default:
			break;
		}
	}

	/**
	 * 通过路径，得到该路径图片的Bitmap对象
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private Bitmap getBitmapByPath(String path) throws IOException {
		URL url = new URL(path);
		Bitmap bm = BitmapFactory.decodeStream(url.openStream());
		return bm;
	}
}