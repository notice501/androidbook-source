package com.pms.alarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlarmActivity extends Activity {
	Button startAlarm = null;
	Button stopAlarm = null;
	PendingIntent sender = null;
	AlarmManager alarm = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取布局控件
		setContentView(R.layout.main);
		startAlarm = (Button) this.findViewById(R.id.startAlarm);
		stopAlarm = (Button) this.findViewById(R.id.stopAlarm);
		startAlarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 设置一个广播
				Intent intent = new Intent(AlarmActivity.this,
						AlarmReceiver.class);
				intent.setAction("com.pms.alarm.AlarmActivity");
				intent.putExtra("data", "这是一个Alarm");
				sender = PendingIntent.getBroadcast(AlarmActivity.this, 0,
						intent, 0);
				// 设定一个五秒后的时间
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.add(Calendar.SECOND, 10);
				// 从系统中获取AlarmManager
				alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
				// 设置Alarm
				alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
						sender);
				// Toast提示
				Toast.makeText(AlarmActivity.this, "闹铃已经设定，十秒钟开始启动",
						Toast.LENGTH_LONG).show();
			}
		});
		stopAlarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (alarm != null && sender != null) {
					// 取消Alarm
					alarm.cancel(sender);
				}
			}
		});
	}
}