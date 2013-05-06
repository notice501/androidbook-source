package com.pms.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity {
	/*������������������������*/
	private static final int ACTIVITY01 = 1;
	private static final int ACTIVITY02 = 2;
	
	/*�����ؼ�����*/
	private TextView tv;
	private Button  bt1, bt2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*ȡ�ÿؼ�����*/
        tv = (TextView) findViewById(R.id.tv);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        
        /*Ϊ��ť�󶨼�����*/
        bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//����һ��Intent����
				Intent intent01 = new Intent(Result.this, Activity02.class);
				startActivityForResult(intent01, ACTIVITY01);//��ת
			}
		});
        
        bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent02 = new Intent(Result.this, Activity03.class);
				startActivityForResult(intent02,ACTIVITY02);
			}
		});
    }
    /*���Ǹ÷�����ȡ����Activity�ķ���ֵ*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)//�ж����ĸ�Activity���ص�ֵ
		{
		case ACTIVITY01:
			if(resultCode==RESULT_OK)
			{
				Uri value = data.getData();//ȡ������
				tv.setText("����Activity02��������ֵ:"+"\n"+value.toString());
			}
			else{
				tv.setText("δ��ֵ");
			}
			break;
		case ACTIVITY02:
			Uri value = data.getData();
			tv.setText("����Activity03��������ֵ:"+"\n"+value.toString());
			break;
			
		}
	}
}