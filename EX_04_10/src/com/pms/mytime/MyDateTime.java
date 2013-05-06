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
	
	/*��������*/
	private DatePicker dp;
	private TimePicker tp;
	private Button     bt_date;
	private Button     bt_time;
	private TextView   tv_date;
	private TextView   tv_time;
	Calendar           c;//java�е�Calendar�࣬��������ʱ�䣬�Ǻ��˽�����ѿ����Լ�����������
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        c = Calendar.getInstance();//���Calendar����
        
        /*��ȡ�����ؼ�����*/
        dp = (DatePicker) findViewById(R.id.dp);
        tp = (TimePicker) findViewById(R.id.tp);
        
        tv_date = (TextView) findViewById(R.id.tvdate);
        tv_time = (TextView) findViewById(R.id.tvtime);
        
        bt_date = (Button) findViewById(R.id.btdate);
        bt_time = (Button) findViewById(R.id.bttime);
        
        /*�����ڿؼ���ʼ��Ϊ��ǰ���ڣ������ü�����*/
        dp.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				
				c.set(year, monthOfYear, dayOfMonth);//�������ڵ�ֵΪ���ڿؼ���ʾ��ֵ
				//��������TextView����ʾ,�·�Ҫ��1
				tv_date.setText(year + "��" + (monthOfYear + 1) + "��" + dayOfMonth + "��");
			}
		});
        
        tp.setIs24HourView(true);//Ĭ��Ϊ12Сʱ�ƣ����������������Ϊ24Сʱ��
        /*Ϊʱ��ؼ���Ӽ�����*/
        tp.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
				c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
				tv_time.setText(hourOfDay + "��" + minute + "��");//��ʱ����TextView����ʾ
			}
		});
        
        /*ΪButton�󶨼�����,�����������ѡ��Ի��򣬶Ի�����÷������������*/
        bt_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new DatePickerDialog(MyDateTime.this,
						 new DatePickerDialog.OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								c.set(year, monthOfYear, dayOfMonth);//�������ڵ�ֵΪ���ڿؼ���ʾ��ֵ
								//��������TextView����ʾ,�·�Ҫ��1
								tv_date.setText(year + "��" + (monthOfYear + 1) + "��" + dayOfMonth + "��");
								
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
				
			}
		});
        /*ΪButton������������������¼�ѡ��Ի���*/
        bt_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new TimePickerDialog(MyDateTime.this,
						new TimePickerDialog.OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
								tv_time.setText(hourOfDay + "��" + minute + "��");//��ʱ����TextView����ʾ
								
							}
						}, c.get(Calendar.HOUR_OF_DAY), Calendar.MINUTE, true).show();

				
			}
		});
    }
}