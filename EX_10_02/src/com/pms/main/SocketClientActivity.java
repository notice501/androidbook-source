package com.pms.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SocketClientActivity extends Activity {
	TextView textView_1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("Socket客户端程序");
		// 得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//声明变量
		InputStream inputStream = null;
		Socket socket = null;
		try {
			//指定客户端请求服务器的IP和端口
			socket = new Socket("192.168.1.7", 7777);
			//得到服务器返回数据
			inputStream = socket.getInputStream();
			StringBuffer sb = new StringBuffer();
			//将流转换成字符串
			InputStreamReader isr = new InputStreamReader(inputStream);
			char buf[] = new char[20];
			int nBufLen = isr.read(buf);
			while (nBufLen != -1) {
				sb.append(new String(buf, 0, nBufLen));
				nBufLen = isr.read(buf);
			}
			//把字符串设置到空间内
			textView_1.setText(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流和Socket
			try {
				if (inputStream != null)
					inputStream.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}