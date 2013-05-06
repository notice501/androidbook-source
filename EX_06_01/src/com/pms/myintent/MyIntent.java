package com.pms.myintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyIntent extends Activity {
	
	/*�����ؼ�����*/
	private EditText et1, et2;
	private Button   bt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*ȡ�ÿؼ�����*/
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        bt = (Button) findViewById(R.id.bt);
        

        /*Ϊ��ť�󶨼�����*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*ȡ��������е�����*/
		        String et1Str = et1.getText().toString();
		        String et2Str = et2.getText().toString();
		        //����Intent���󣬲����ֱ�Ϊ�����ģ�Ҫ��ת��Activity��
		        Intent intent = new Intent(MyIntent.this, SecondActivity.class);
		        //��Ҫ���ݵ�ֵ���ӵ�Intent����
		        intent.putExtra("et1", et1Str);
		        intent.putExtra("et2", et2Str);
		        //������Intent����ʵ����ת
		        startActivity(intent);
			}
		});
        
        
        
    }
}