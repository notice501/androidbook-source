package com.pms.myintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyIntent extends Activity {
	
	/*声明控件对象*/
	private EditText et1, et2;
	private Button   bt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*取得控件对象*/
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        bt = (Button) findViewById(R.id.bt);
        

        /*为按钮绑定监听器*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*取得输入框中的内容*/
		        String et1Str = et1.getText().toString();
		        String et2Str = et2.getText().toString();
		        //创建Intent对象，参数分别为上下文，要跳转的Activity类
		        Intent intent = new Intent(MyIntent.this, SecondActivity.class);
		        //将要传递的值附加到Intent对象
		        intent.putExtra("et1", et1Str);
		        intent.putExtra("et2", et2Str);
		        //启动该Intent对象，实现跳转
		        startActivity(intent);
			}
		});
        
        
        
    }
}