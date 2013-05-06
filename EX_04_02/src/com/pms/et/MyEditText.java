package com.pms.et;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MyEditText extends Activity {
	
	private EditText et;
	private EditText et1;
	private TextView tv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et = (EditText) findViewById(R.id.et);
        et1 = (EditText) findViewById(R.id.et1);
        tv = (TextView) findViewById(R.id.tv);
        
        /*ΪEditText�����س���,����һ�ھͻὲandroid�е��¼�����*/
        et1.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode==KeyEvent.KEYCODE_ENTER)
				{
					tv.setText("�����û���Ϊ��"+et.getText().toString()+"\n"+"��������Ϊ��"
						+et1.getText().toString());//���»س�������ʾ�ı����ı�Ϊ������е�����
				}
				
				return false;
			}
		});
}
}