package com.pms.main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class JavaNetGetAndPost {
	public static void main(String[] args) throws IOException {
		// 1.声明变量
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			// 2.设置URL打开连接，并且带参数testParam1=110
			url = new URL("http://192.168.1.7:8080/exa/index.jsp?testParam1=110");
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setRequestMethod("GET");
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setDoInput(true);
			// 3.用OutputStream输出一个testParam2=119的参数
			outputStream = httpurlconnection.getOutputStream();
			outputStream.write("testParam2=119".getBytes());
			outputStream.flush();
			// 4.用InputStream得到输入数据并且打印数据到控制台（此处打印以文本形式的HTML）
			inputStream = httpurlconnection.getInputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = inputStream.read(b)) != -1) {
				System.out.println(new String(b,"gb2312"));
				b = new byte[1024];
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 4.关闭流和连接
			if (outputStream != null)
				outputStream.close();
			if (inputStream != null)
				inputStream.close();
			if (httpurlconnection != null)
				httpurlconnection.disconnect();
		}
	}
}
