package com.pms.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaNetGet {
	public static void main(String[] args) throws IOException {
		// 1.声明变量
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		InputStream inputStream = null;
		try {
			// 2.设置URL并且打开连接(模拟在百度搜索"android"关键字)
			url = new URL("http://172.30.40.122:8080/json/jsonServlet");
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setRequestMethod("GET");
			// 3.得到请求响应码(此处只是验证是否成功请求和响应)
			int code = httpurlconnection.getResponseCode();
			System.out.println("返回码：   " + code);
			// 4.打印数据(打印搜索"android"关键字的结果)
			inputStream = httpurlconnection.getInputStream();
			byte [] b=new byte[1024];
			int i=0;
			while((i=inputStream.read(b))!=-1){
				System.out.println(new String(b));
				b=new byte[1024];
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 4.关闭流和连接
			if (inputStream != null)
				inputStream.close();
			if (httpurlconnection != null)
				httpurlconnection.disconnect();
		}
	}
}
