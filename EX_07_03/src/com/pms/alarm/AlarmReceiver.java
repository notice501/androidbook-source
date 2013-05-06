package com.pms.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		//接收广播
		if (intent.getAction().equals("com.pms.alarm.AlarmActivity")) {
			//获取Intent传递过来的数据
			String data=intent.getStringExtra("data");
			//Toast提示
			Toast.makeText(context, data, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "没有Alarm", Toast.LENGTH_LONG)
					.show();
		}
	}
}
