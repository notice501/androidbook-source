package com.pms.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlarmAlert extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*创建一个对话框*/
		new AlertDialog.Builder(this)
		.setTitle("闹钟")
		.setMessage("起床吧！")
		.setPositiveButton("关了，起床！", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();//关闭该Activity
			}
		})
		.show();
		
	}
	
}
