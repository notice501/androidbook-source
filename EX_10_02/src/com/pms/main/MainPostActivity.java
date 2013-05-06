package com.pms.main;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainPostActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		// 声明变量
		HttpClient httpClient = null;
		HttpPost httpRequest = null;
		HttpResponse httpResponse = null;
		try {
			httpUrl = "http://192.168.1.7:8080/exa/index.jsp";
			// 取得默认的HttpClient
			httpClient = new DefaultHttpClient();
			// HttpPost连接对象
			httpRequest = new HttpPost(httpUrl);
			// 使用NameValuePair来保存要传递的Post参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 添加要传递的参数
			params.add(new BasicNameValuePair("testParam1", "110"));
			// 设置字符集
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312");
			// 请求httpRequest
			httpRequest.setEntity(httpentity);
			// 取得HttpResponse
			httpResponse = httpClient.execute(httpRequest);
			// HttpStatus.SC_OK表示连接成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				textView_1.setText(strResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}