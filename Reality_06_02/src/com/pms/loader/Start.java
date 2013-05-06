package com.pms.loader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Start extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		//����Intent����
		Intent startIntent = new Intent(context, Loader.class);
		//Ϊ������Activity�½�һ������
		startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//����Activity
		context.startActivity(startIntent);
	}

}
