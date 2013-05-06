package com.pms.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity03 extends Activity{
	
	/*�����ؼ�����*/
	private EditText et;
	private Button   bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity03);
		
		/*��ÿؼ�����*/
		et = (EditText) findViewById(R.id.et02);
		bt = (Button) findViewById(R.id.bt02);
		
		/*Ϊ��ť�󶨼�����*/
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String et_value = et.getText().toString();//���������ֵ
				Uri data = Uri.parse(et_value);//ת��ΪUri����
				Intent intent = new Intent(null,data);//ת��ΪIntent����
				setResult(RESULT_OK, intent);//���÷���ֵ
				finish();
			}
		});
	}
	

}
