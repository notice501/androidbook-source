package com.pms.main;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button bt_start = null;
	Button bt_stop = null;
	Button bt_bind = null;
	Button bt_unbind = null;

	MyService mBoundService = null;
	boolean isBound = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bt_start = (Button) this.findViewById(R.id.bt_start);
		bt_stop = (Button) this.findViewById(R.id.bt_stop);
		bt_bind = (Button) this.findViewById(R.id.bt_bind);
		bt_unbind = (Button) this.findViewById(R.id.bt_unbind);
		bt_start.setVisibility(View.GONE);
		bt_stop.setVisibility(View.GONE);
		// 启动服务
		bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("点击[Start服务]");
				Intent service = new Intent(MainActivity.this, MyService.class);
				MainActivity.this.startService(service);
			}
		});
		// 停止服务
		bt_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("点击[Stop服务]");
				Intent service = new Intent(MainActivity.this, MyService.class);
				MainActivity.this.stopService(service);
			}
		});
		// Bind服务
		bt_bind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("点击[Bind服务]");
				Intent service = new Intent(MainActivity.this, MyService.class);
				MainActivity.this.bindService(service, mConnection,
						Context.BIND_AUTO_CREATE);
				isBound = true; 
			}
		});
		// UnBind服务
		bt_unbind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("点击[UnBind服务]");
				if (isBound)
					MainActivity.this.unbindService(mConnection);
			}
		});
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			System.out.println("onServiceConnected");
			mBoundService = ((MyService.LocalBinder) service).getService();
		}

		public void onServiceDisconnected(ComponentName className) {
			System.out.println("onServiceConnected");
			mBoundService = null;
		}
	};

}