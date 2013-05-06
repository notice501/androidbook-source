package com.pms.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NetZhCNActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("研究中文乱码问题");
		//得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//声明网络操作需要的变量
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		InputStream inputStream = null;
		try {
			// 2.设置URL并且打开连接
			url = new URL("http://192.168.1.7:8080/exa/json");
			httpurlconnection = (HttpURLConnection) url.openConnection();
			// 3.得到输入流并转换为字符串
			inputStream = httpurlconnection.getInputStream();
			String strResult="";
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = inputStream.read(b)) != -1) {
				strResult+=new String(b,"gb2312");
				b = new byte[1024];
			}
			//4.显示到控件上
			textView_1.setText(strResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6.关闭流和连接
			try {
				if (inputStream != null)
					inputStream.close();
				if (httpurlconnection != null)
					httpurlconnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}