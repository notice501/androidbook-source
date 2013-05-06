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
        
        /*为EditText监听回车键,在下一节就会讲android中的事件处理*/
        et1.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode==KeyEvent.KEYCODE_ENTER)
				{
					tv.setText("您的用户名为："+et.getText().toString()+"\n"+"您的密码为："
						+et1.getText().toString());//按下回车键即显示文本，文本为输入框中的内容
				}
				
				return false;
			}
		});
}
}