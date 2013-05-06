package com.pms.myintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity{
	
	//����TextView����
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		//ȡ��TextView����
		tv = (TextView) findViewById(R.id.tv);
		
		//ȡ��������Activity��Intent����
		Intent intent =getIntent();
		/*ȡ��Intent�и��ӵ�����*/
		String first = intent.getStringExtra("et1");
		String second = intent.getStringExtra("et2");
		
		//����õ����
		int result = Integer.parseInt(first) + Integer.parseInt(second);
		
		//����TextView��ʾ���ı�
		tv.setText("������Ϊ:"+String.valueOf(result));
		
		
	}
	
}
