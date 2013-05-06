package com.pms.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activity02 extends Activity{
	
	/*声明控件对象*/
	private EditText et;
	private Button   bt, bt_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity02);
		
		/*取得控件对象*/
		et = (EditText) findViewById(R.id.et01);
		bt = (Button) findViewById(R.id.bt);
		bt_cancel = (Button) findViewById(R.id.bt_cancel);
		
		//分别为两个按钮绑定监听器
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//取得输入框的内容
				String et_value = et.getText().toString();
				//创建一个Uri对象
				Uri data = Uri.parse(et_value);
				//创建一个Intent对象
				Intent result = new Intent(null,data);
				//设置需要传递的值和结果码
				setResult(RESULT_OK, result);
				finish();//关闭该activity
			}
		});
		
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//只设置结果码
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
	}
	

}
