package com.pms.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlarmAlert extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*����һ���Ի���*/
		new AlertDialog.Builder(this)
		.setTitle("����")
		.setMessage("�𴲰ɣ�")
		.setPositiveButton("���ˣ��𴲣�", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();//�رո�Activity
			}
		})
		.show();
		
	}
	
}
