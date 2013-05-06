package com.pms.bluetooth;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

public class ServerActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("蓝牙服务端");
		// 声明变量
		BluetoothAdapter bluetooth = null;//本地蓝牙设备
		BluetoothServerSocket serverSocket = null;// 蓝牙设备Socket服务端
		BluetoothSocket socket = null;// 蓝牙设备Socket客户端
		OutputStream os = null;// 蓝牙输出流
		InputStream is = null;// 蓝牙输入流
		try {
			// 1.得到本地设备
			bluetooth = BluetoothAdapter.getDefaultAdapter();
			// 2.创建蓝牙socket服务器
			serverSocket = bluetooth
					.listenUsingRfcommWithServiceRecord("btspp", UUID
							.fromString("00000000-2527-eef3-ffff-ffffe3160865"));
			// 3.阻塞等待socket客户端请求
			socket = serverSocket.accept();
			if (socket != null) {
				// 4.处理输出流
				os = socket.getOutputStream();
				os.write("我是服务器！".getBytes());
				if (os != null) {
					os.flush();
					os.close();
				}
				// 5.处理输入流
				is = socket.getInputStream();
				byte[] bytes = new byte[is.available()];
				is.read(bytes);
				System.out.println("服务器端读取客户端传输的数据时：" + new String(bytes));
				if (is != null)
					is.close();
				if (socket != null)
					socket.close();
				if (serverSocket != null)
					serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}