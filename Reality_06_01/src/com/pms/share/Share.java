package com.pms.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Share extends Activity {
	
	private Button bt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //ȡ��Button����
        bt = (Button) findViewById(R.id.bt);
        //ΪButotn�󶨼�����
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);//����Intent����ִ�з��Ͷ���
				intent.setType("text/plain");//�������ݵ�MIME����
				intent.putExtra(Intent.EXTRA_TEXT, "�ܼ򵥣�");//���Ҫ���ݵ�����
				startActivity(Intent.createChooser(intent, getTitle()));//�������intent��������ѡ��ı���
			}
		});
    }
}