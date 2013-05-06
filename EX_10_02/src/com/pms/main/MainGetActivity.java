package com.pms.main;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainGetActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//声明变量
		HttpClient httpClient = null;
		HttpGet httpRequest = null;
		HttpResponse httpResponse = null;
		try {
			httpUrl = "http://192.168.1.7:8080/exa/index.jsp?testParam1=110";
			// 构造HttpClient的实例
			httpClient = new DefaultHttpClient();
			// HttpGet连接对象
			httpRequest = new HttpGet(httpUrl);
			// 请求HttpClient，取得HttpResponse
			httpResponse = httpClient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				//设置到页面控件上
				textView_1.setText(strResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}