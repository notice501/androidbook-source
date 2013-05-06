package com.pms.server;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pms.AidlService;

/**
 * 客户端服务
 */
public class MainActivity extends Activity {
	Button bt_bind = null;
	Button bt_data = null;
	TextView tx_data = null;
	AidlService aidlService = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bt_bind = (Button) this.findViewById(R.id.bt_bind);
		bt_data = (Button) this.findViewById(R.id.bt_data);
		tx_data = (TextView) this.findViewById(R.id.tx_data);
		//绑定远程服务
		bt_bind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 绑定AIDL
				Intent service = new Intent(AidlService.class.getName());
				bindService(service, connection, BIND_AUTO_CREATE);
			}
		});
		//获取另外一个程序服务的数据
		bt_data.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					if (aidlService != null)
						tx_data.setText("远程结果：" + aidlService.getMyName());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 创建远程调用对象
	private ServiceConnection connection = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 从远程service中获得AIDL实例化对象
			aidlService = AidlService.Stub.asInterface(service);
		}
		public void onServiceDisconnected(ComponentName name) {
			aidlService = null;
		}
	};
}