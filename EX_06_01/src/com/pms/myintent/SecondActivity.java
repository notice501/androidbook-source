package com.pms.myintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity{
	
	//声明TextView对象
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		//取得TextView对象
		tv = (TextView) findViewById(R.id.tv);
		
		//取得启动该Activity的Intent对象
		Intent intent =getIntent();
		/*取出Intent中附加的数据*/
		String first = intent.getStringExtra("et1");
		String second = intent.getStringExtra("et2");
		
		//计算得到结果
		int result = Integer.parseInt(first) + Integer.parseInt(second);
		
		//设置TextView显示的文本
		tv.setText("计算结果为:"+String.valueOf(result));
		
		
	}
	
}
