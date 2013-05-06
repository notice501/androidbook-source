package com.pms.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity03 extends Activity{
	
	/*声明控件对象*/
	private EditText et;
	private Button   bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity03);
		
		/*获得控件对象*/
		et = (EditText) findViewById(R.id.et02);
		bt = (Button) findViewById(R.id.bt02);
		
		/*为按钮绑定监听器*/
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String et_value = et.getText().toString();//获得输入框的值
				Uri data = Uri.parse(et_value);//转换为Uri对象
				Intent intent = new Intent(null,data);//转换为Intent对象
				setResult(RESULT_OK, intent);//设置返回值
				finish();
			}
		});
	}
	

}
