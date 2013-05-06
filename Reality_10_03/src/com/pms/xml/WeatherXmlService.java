package com.pms.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.pms.bean.WeatherCurrentCondition;
import com.pms.bean.WeatherForecastCondition;
import com.pms.bean.WeatherForecastInformation;

public class WeatherXmlService {
	/**
	 * 通过google查询天气的路径，得到Xml组装的List信息
	 * @param queryString
	 *            路径
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getWeatherInfos(String queryString)
			throws Exception {
		// 声明该List是将三个实体对象都封装起来，返回到Activity
		List<Object> objList = new ArrayList<Object>();
		// 声明三个实体对象，未来天气情况是多个，所以声明为List
		WeatherForecastInformation forecastInformation = null;// 当前预测城市相关信息(GPS、省份、当前时间等)
		WeatherCurrentCondition currentCondition = null;// 当前天气情况
		List<WeatherForecastCondition> forecastConditionList = null; // 未来几天的天气列表
		WeatherForecastCondition forecastCondition = null;
		// 以下通过Pull解析Google服务器接口返回Xml，分别组装到三个实体对象中
		XmlPullParser parser = Xml.newPullParser();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(queryString);
		HttpResponse response = client.execute(post);
		HttpEntity httpEntity = response.getEntity();
		// 由于服务器返回来的数据编码是“ISO-8859-1”所以此处需要转码
		String string = EntityUtils.toString(httpEntity, "UTF-8");
		InputStream inputStream = new ByteArrayInputStream(string.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				forecastConditionList = new ArrayList<WeatherForecastCondition>();
				break;
			case XmlPullParser.START_TAG:
				if ("forecast_information".equalsIgnoreCase(parser.getName())) {
					forecastInformation = new WeatherForecastInformation();
					break;
				} else if ("current_conditions".equalsIgnoreCase(parser
						.getName())) {
					currentCondition = new WeatherCurrentCondition();
					break;
				} else if ("forecast_conditions".equalsIgnoreCase(parser
						.getName())) {
					forecastCondition = new WeatherForecastCondition();
					break;
				}
				// 预测城市相关信息
				if (forecastInformation != null) {
					if ("city".equalsIgnoreCase(parser.getName())) {
						forecastInformation
								.setCity(parser.getAttributeValue(0));
					} else if ("postal_code".equalsIgnoreCase(parser.getName())) {
						forecastInformation.setPostal_code(parser
								.getAttributeValue(0));
					} else if ("latitude_e6".equalsIgnoreCase(parser.getName())) {
						forecastInformation.setLatitude_e6(parser
								.getAttributeValue(0));
					} else if ("longitude_e6"
							.equalsIgnoreCase(parser.getName())) {
						forecastInformation.setLongitude_e6(parser
								.getAttributeValue(0));
					} else if ("forecast_date".equalsIgnoreCase(parser
							.getName())) {
						forecastInformation.setForecast_date(parser
								.getAttributeValue(0));
					} else if ("current_date_time".equalsIgnoreCase(parser
							.getName())) {
						forecastInformation.setCurrent_date_time(parser
								.getAttributeValue(0));
					} else if ("unit_system".equalsIgnoreCase(parser.getName())) {
						forecastInformation.setUnit_system(parser
								.getAttributeValue(0));
					}
					break;
				}
				// 当前天气情况
				if (currentCondition != null) {
					if ("condition".equalsIgnoreCase(parser.getName())) {
						currentCondition.setCondition(parser
								.getAttributeValue(0));
					} else if ("temp_f".equalsIgnoreCase(parser.getName())) {
						currentCondition.setTempFahrenheit(parser
								.getAttributeValue(0));
					} else if ("temp_c".equalsIgnoreCase(parser.getName())) {
						currentCondition.setTempCelcius(parser
								.getAttributeValue(0));
					} else if ("humidity".equalsIgnoreCase(parser.getName())) {
						currentCondition.setHumidity(parser
								.getAttributeValue(0));
					} else if ("icon".equalsIgnoreCase(parser.getName())) {
						currentCondition
								.setIconURL(parser.getAttributeValue(0));
					} else if ("wind_condition".equalsIgnoreCase(parser
							.getName())) {
						currentCondition.setWindCondition(parser
								.getAttributeValue(0));
					}
					break;
				}
				// 未来几天的天气
				if (forecastCondition != null) {
					if ("day_of_week".equalsIgnoreCase(parser.getName())) {
						forecastCondition.setDayofWeek(parser
								.getAttributeValue(0));
					} else if ("low".equalsIgnoreCase(parser.getName())) {
						forecastCondition.setTempMinCelsius(parser
								.getAttributeValue(0));
					} else if ("high".equalsIgnoreCase(parser.getName())) {
						forecastCondition.setTempMaxCelsius(parser
								.getAttributeValue(0));
					} else if ("icon".equalsIgnoreCase(parser.getName())) {
						forecastCondition.setIconURL(parser
								.getAttributeValue(0));
					} else if ("condition".equalsIgnoreCase(parser.getName())) {
						forecastCondition.setCondition(parser
								.getAttributeValue(0));
					}
					break;
				}

				break;

			case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
				if ("forecast_information".equalsIgnoreCase(parser.getName())) {
					objList.add(forecastInformation);
				} else if ("current_conditions".equalsIgnoreCase(parser
						.getName())) {
					objList.add(currentCondition);
				} else if ("forecast_conditions".equalsIgnoreCase(parser
						.getName())) {
					forecastConditionList.add(forecastCondition);
				}
				if ("forecast_information".equalsIgnoreCase(parser.getName())
						|| "current_conditions".equalsIgnoreCase(parser
								.getName())
						|| "forecast_conditions".equalsIgnoreCase(parser
								.getName())) {
					forecastInformation = null;
					currentCondition = null;
					forecastCondition = null;
				}
				break;
			}
			event = parser.next();// 进入下一个元素并触发相应事件
		}
		// 将未来天气列表也放入List中
		objList.add(forecastConditionList);
		if (inputStream != null)
			inputStream.close();
		return objList;
	}

}
