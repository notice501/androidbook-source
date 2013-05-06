package com.pms.main;
import java.net.HttpURLConnection;
import java.net.URL;
public class JavaNetDemo {
	public static void main(String[] args){
		//1.声明变量
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		try {
			//2.设置URL并且打开连接
			url = new URL("http://www.csdn.net/");
			httpurlconnection = (HttpURLConnection) url.openConnection();
			//3.得到请求响应码(此处只是验证是否成功请求和响应)
			int code = httpurlconnection.getResponseCode();
			System.out.println("返回码：   " + code);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//4.关闭流和连接
			if (httpurlconnection != null)
				httpurlconnection.disconnect();
		}
	}
}
