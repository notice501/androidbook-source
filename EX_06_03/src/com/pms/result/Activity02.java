package com.pms.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity02 extends Activity{
	
	/*�����ؼ�����*/
	private EditText et;
	private Button   bt, bt_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity02);
		
		/*ȡ�ÿؼ�����*/
		et = (EditText) findViewById(R.id.et01);
		bt = (Button) findViewById(R.id.bt);
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		
		//�ֱ�Ϊ������ť�󶨼�����
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ȡ������������
				String et_value = et.getText().toString();
				//����һ��Uri����
				Uri data = Uri.parse(et_value);
				//����һ��Intent����
				Intent result = new Intent(null,data);
				//������Ҫ���ݵ�ֵ�ͽ����
				setResult(RESULT_OK, result);
				finish();//�رո�activity
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ֻ���ý����
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
	}
	

}
