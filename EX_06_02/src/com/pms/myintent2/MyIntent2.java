package com.pms.myintent2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyIntent2 extends Activity {
	
	private Button bt;  
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*�õ�Button���������*/
        bt = (Button) findViewById(R.id.bt);
        /*ΪButton��Ӽ�����*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��ʽ����activity
		        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("schemodemo://com.notice/path"));
		        startActivity(intent);
				
			}
		});

    }
}