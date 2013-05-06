package com.pms.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainJsonActivity extends Activity {
	TextView textView_1 = null;
	String httpUrl = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("得到网络Json字符串并且解析");
		//得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//声明网络操作需要的变量
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		InputStream inputStream = null;
		try {
			// 2.设置URL并且打开连接
			url = new URL("http://172.30.40.122:8080/json/jsonServlet");
			httpurlconnection = (HttpURLConnection) url.openConnection();
			// 3.得到输入流并转换为字符串
			inputStream = httpurlconnection.getInputStream();
			String strResult="";
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = inputStream.read(b)) != -1) {
				strResult+=new String(b);
				b = new byte[1024];
			}
			System.out.println("网络中得到原始字符串："+strResult);
			//4.解析Json字符串
			System.out.println("开始解析Json字符串............");
			StringBuffer strJson=new StringBuffer();
			JSONObject jsonObject = new JSONObject(strResult); 			
			strJson.append("姓名："+jsonObject.get("name")+"\n");
			strJson.append("年龄："+jsonObject.get("age")+"\n");
			strJson.append("地址："+jsonObject.get("address")+"\n");			
			JSONObject addressObject=jsonObject.getJSONObject("address");//注意嵌套Json的解析
			strJson.append("城市："+addressObject.get("city")+"\n");
			strJson.append("街道："+addressObject.get("street")+"\n");
			strJson.append("邮编："+addressObject.get("postcode")+"\n");				
			strJson.append("电话："+jsonObject.get("tel")+"\n");
			System.out.println("结束解析Json字符串............");
			//5.显示到控件上
			textView_1.setText(strJson.toString());
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