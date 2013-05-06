package com.pms.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyBroadCast extends Activity {
	
	private Button bt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        bt = (Button) findViewById(R.id.bt);//�õ�Button����
        /*ΪButton�󶨼����¼�*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//����Intent������������
				Intent intent = new Intent("com.pms.broadcast");
				sendBroadcast(intent);//���͹㲥
			}
		});
    }
}