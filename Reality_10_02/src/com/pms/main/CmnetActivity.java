package com.pms.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CmnetActivity extends Activity {
	TextView textView_1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		Socket socket;
		try {
			//实例出Socket
			socket = new Socket("blog.sina.com.cn", 80);
			//输出流
			OutputStream os = socket.getOutputStream();
			//输入流
			InputStream ins = socket.getInputStream();
			StringBuffer sb = new StringBuffer();

			String host = "blog.sina.com.cn";
			String url = "/rss/blogmc.xml";
			String method = "GET";
			// 第1行：方法，请求的内容，HTTP协议的版本
			// 下载一般可以用GET方法，请求的内容是“/rss/blogmc.xml”，HTTP协议的版本是指浏览器支持的版本，对于下载软件来说无所谓，所以用1.1版
			// “HTTP/1.1”；
			sb.append(method + " " + url + " HTTP/1.1\r\n");
			// 主机名，格式为“Host:主机”
			sb.append("Host:" + host + "\r\n");
			// 接受的数据类型
			sb.append("Accept: :*/* \r\n");
			// 接受的数据语言，可以不设置
			sb.append("Accept-Language: zh-cn\r\n");
			// 连接设置 设定为一直保持连接
			sb.append("Connection: Keep-Alive\r\n");
			// 注意最后一定要有\r\n回车换行
			sb.append("\r\n");
			// 接收Web服务器返回HTTP响应包
			os.write(sb.toString().getBytes());
			os.flush();
			InputStreamReader ireader = new InputStreamReader(ins);
			BufferedReader reader = new BufferedReader(ireader);
			String str = "";
			sb=new StringBuffer();
			while ((str = reader.readLine()) != null) {
				sb.append(str+"\n");
			}
			System.out.println(sb.toString());// 读取内容
			reader.close();
			ireader.close();
			os.close();
			textView_1.setText(sb.toString());//读取内容写入控件中
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}