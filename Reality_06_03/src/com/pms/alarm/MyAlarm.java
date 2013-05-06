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
	
	/*������������*/
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
        //ȡ�ø�������
        bt_on = (Button) findViewById(R.id.bt_on);
        bt_off = (Button) findViewById(R.id.bt_off);
        tv = (TextView) findViewById(R.id.tv);
        c = Calendar.getInstance();//ȡ��һ��Calendar����
        //ȡ��һ��AlarmManager����
        am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        
        /*�󶨼�����*/
        bt_on.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/* ȡ�ð��°�ťʱ��ʱ����ΪTimePickerDialog��Ĭ��ֵ */
		        c.setTimeInMillis(System.currentTimeMillis());
		        int mHour=c.get(Calendar.HOUR_OF_DAY); 
		        int mMinute=c.get(Calendar.MINUTE);
		        //����һ��TimePickerDialog
		        new TimePickerDialog(MyAlarm.this, new OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						/*����Calendar�����ʱ��*/
						c.setTimeInMillis(System.currentTimeMillis());
	                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
	                    c.set(Calendar.MINUTE, minute);
	                    c.set(Calendar.SECOND, 0);
	                    c.set(Calendar.MILLISECOND, 0);
	                    //����Intent����
	                    intent = new Intent(MyAlarm.this, AlarmReceiver.class);
	                    //����PendingIntent�����͹㲥
	                    pIntent = PendingIntent.getBroadcast(MyAlarm.this, 0, intent, 0);
	                    //��������
	                    am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pIntent);
	                    //TextView��ʾ���õ�ʱ��
	                    tv.setText("���õ�����ʱ��Ϊ:" + hourOfDay + ":" + minute);
					}
				},mHour, mMinute, true).show();
			}
		});
        
        bt_off.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				intent = new Intent(MyAlarm.this, AlarmReceiver.class);
	            pIntent = PendingIntent.getBroadcast(MyAlarm.this, 0, intent, 0);
	            am.cancel(pIntent);//ȡ������
	            tv.setText("����ȡ��");
			}
		});
    }
}