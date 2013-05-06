package com.pms.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		//����һ��Intent������ת��AlarmAlert
		Intent AlarmIntent = new Intent(context, AlarmAlert.class);
		//���ñ�ʶ��ΪActivity�½�һ������
		AlarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(AlarmIntent);//����Activity
	}

}
