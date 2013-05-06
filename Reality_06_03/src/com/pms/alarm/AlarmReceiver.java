package com.pms.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		//创建一个Intent对象，跳转到AlarmAlert
		Intent AlarmIntent = new Intent(context, AlarmAlert.class);
		//设置标识，为Activity新建一个任务
		AlarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(AlarmIntent);//启动Activity
	}

}
