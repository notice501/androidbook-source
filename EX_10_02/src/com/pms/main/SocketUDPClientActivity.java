package com.pms.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SocketUDPClientActivity extends Activity {
	TextView textView_1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("SocketUDP客户端程序");
		// 得到控件
		textView_1 = (TextView) this.findViewById(R.id.textView_1);
		//声明变量
		try {
			int port = 7777;//端口
			String str = "微度网";//发送到服务器字符串
			DatagramSocket ds = new DatagramSocket();
			DatagramPacket sendDp = new DatagramPacket(str.getBytes(), str
					.getBytes().length, InetAddress.getByName("192.168.1.7"),
					port);
			ds.send(sendDp);
			byte[] buf = new byte[100];
			DatagramPacket receiveDp = new DatagramPacket(buf, 100);// 创建长度为100的数据接收包
			ds.receive(receiveDp);// 套接字接受数据包
			System.out.println(new String(buf, 0, receiveDp.getLength()));
			textView_1.setText("这是UDP客户端\n"+new String(buf, 0, receiveDp.getLength()));
			ds.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}