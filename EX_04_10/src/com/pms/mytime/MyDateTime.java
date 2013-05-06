package com.pms.mytime;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class MyDateTime extends Activity {
	
	/*声明对象*/
	private DatePicker dp;
	private TimePicker tp;
	private Button     bt_date;
	private Button     bt_time;
	private TextView   tv_date;
	private TextView   tv_time;
	Calendar           c;//java中的Calendar类，用来处理时间，是很了解的朋友可以自己查阅下资料
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        c = Calendar.getInstance();//获得Calendar对象
        
        /*获取各个控件对象*/
        dp = (DatePicker) findViewById(R.id.dp);
        tp = (TimePicker) findViewById(R.id.tp);
        
        tv_date = (TextView) findViewById(R.id.tvdate);
        tv_time = (TextView) findViewById(R.id.tvtime);
        
        bt_date = (Button) findViewById(R.id.btdate);
        bt_time = (Button) findViewById(R.id.bttime);
        
        /*将日期控件初始化为当前日期，并设置监听器*/
        dp.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				
				c.set(year, monthOfYear, dayOfMonth);//设置日期的值为日期控件显示的值
				//将日期在TextView中显示,月份要加1
				tv_date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
			}
		});
        
        tp.setIs24HourView(true);//默认为12小时制，用这个方法可以设为24小时制
        /*为时间控件添加监听器*/
        tp.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
				c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
				tv_time.setText(hourOfDay + "点" + minute + "分");//将时间在TextView中显示
			}
		});
        
        /*为Button绑定监听器,点击弹出日期选择对话框，对话框的用法后面会具体介绍*/
        bt_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new DatePickerDialog(MyDateTime.this,
						 new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								c.set(year, monthOfYear, dayOfMonth);//设置日期的值为日期控件显示的值
								//将日期在TextView中显示,月份要加1
								tv_date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
								
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
				
			}
		});
        /*为Button帮点监听器，点击弹出事件选择对话框*/
        bt_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new TimePickerDialog(MyDateTime.this,
						new TimePickerDialog.OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
								tv_time.setText(hourOfDay + "点" + minute + "分");//将时间在TextView中显示
								
							}
						}, c.get(Calendar.HOUR_OF_DAY), Calendar.MINUTE, true).show();

				
			}
		});
    }
}