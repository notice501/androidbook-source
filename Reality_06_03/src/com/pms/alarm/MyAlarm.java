package com.pms.alarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MyAlarm extends Activity {
	
	/*声明各个对象*/
	private Button bt_on;
	private Button bt_off;
	private TextView tv;
	private Calendar c;
	Intent intent;
	PendingIntent pIntent;
	AlarmManager am;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //取得各个对象
        bt_on = (Button) findViewById(R.id.bt_on);
        bt_off = (Button) findViewById(R.id.bt_off);
        tv = (TextView) findViewById(R.id.tv);
        c = Calendar.getInstance();//取得一个Calendar对象
        //取得一个AlarmManager对象
        am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        
        /*绑定监听器*/
        bt_on.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/* 取得按下按钮时的时间做为TimePickerDialog的默认值 */
		        c.setTimeInMillis(System.currentTimeMillis());
		        int mHour=c.get(Calendar.HOUR_OF_DAY); 
		        int mMinute=c.get(Calendar.MINUTE);
		        //创建一个TimePickerDialog
		        new TimePickerDialog(MyAlarm.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						/*设置Calendar对象的时间*/
						c.setTimeInMillis(System.currentTimeMillis());
	                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
	                    c.set(Calendar.MINUTE, minute);
	                    c.set(Calendar.SECOND, 0);
	                    c.set(Calendar.MILLISECOND, 0);
	                    //创建Intent对象
	                    intent = new Intent(MyAlarm.this, AlarmReceiver.class);
	                    //创建PendingIntent，发送广播
	                    pIntent = PendingIntent.getBroadcast(MyAlarm.this, 0, intent, 0);
	                    //设置闹钟
	                    am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);
	                    //TextView显示设置的时间
	                    tv.setText("设置的闹钟时间为:" + hourOfDay + ":" + minute);
					}
				},mHour, mMinute, true).show();
			}
		});
        
        bt_off.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				intent = new Intent(MyAlarm.this, AlarmReceiver.class);
	            pIntent = PendingIntent.getBroadcast(MyAlarm.this, 0, intent, 0);
	            am.cancel(pIntent);//取消闹钟
	            tv.setText("闹钟取消");
			}
		});
    }
}