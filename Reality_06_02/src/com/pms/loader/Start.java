package com.pms.loader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Start extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		//创建Intent对象
		Intent startIntent = new Intent(context, Loader.class);
		//为启动的Activity新建一个任务
		startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//启动Activity
		context.startActivity(startIntent);
	}

}
