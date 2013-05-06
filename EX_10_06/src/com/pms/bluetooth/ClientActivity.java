package com.pms.bluetooth;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

public class ClientActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("蓝牙客户端");
		//声明变量
		BluetoothAdapter bluetooth = null;//本地蓝牙设备
		BluetoothDevice device = null;//远程蓝牙设备
		BluetoothSocket socket = null;//蓝牙socket客户端
		OutputStream os = null;//输出流
		InputStream is = null;//输入流
		try {
			//1 得到本地蓝牙设备的默认适配器
			bluetooth = BluetoothAdapter.getDefaultAdapter();
			//2通过本地蓝牙设备得到远程蓝牙设备
			device = bluetooth.getRemoteDevice("A0:75:91:E0:88:D3");
			//3 根据UUID 创建并返回一个BluetoothSocket 	
			socket = device.createRfcommSocketToServiceRecord(UUID
					.fromString("00000000-2527-eef3-ffff-ffffe3160865"));
			if (socket != null) {
				// 连接
				socket.connect();
				//4处理客户端输出流
				os = socket.getOutputStream();
				os.write("我是客户端！".getBytes());
				os.flush();
				os.close();
				//5处理客户端输入流
				is = socket.getInputStream();
				byte[] b = new byte[is.available()];
				is.read(b);
				System.out.println("客户端接收到服务器传输的数据：：" + new String(b));
				is.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}