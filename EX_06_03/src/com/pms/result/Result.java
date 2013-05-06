package com.pms.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity {
	/*声明两个常量，用作请求码*/
	private static final int ACTIVITY01 = 1;
	private static final int ACTIVITY02 = 2;
	
	/*声明控件对象*/
	private TextView tv;
	private Button  bt1, bt2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*取得控件对象*/
        tv = (TextView) findViewById(R.id.tv);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        
        /*为按钮绑定监听器*/
        bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//创建一个Intent对象
				Intent intent01 = new Intent(Result.this, Activity02.class);
				startActivityForResult(intent01, ACTIVITY01);//跳转
			}
		});
        
        bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent02 = new Intent(Result.this, Activity03.class);
				startActivityForResult(intent02,ACTIVITY02);
			}
		});
    }
    /*覆盖该方法，取得子Activity的返回值*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)//判断是哪个Activity传回的值
		{
		case ACTIVITY01:
			if(resultCode==RESULT_OK)
			{
				Uri value = data.getData();//取得数据
				tv.setText("这是Activity02传过来的值:"+"\n"+value.toString());
			}
			else{
				tv.setText("未传值");
			}
			break;
		case ACTIVITY02:
			Uri value = data.getData();
			tv.setText("这是Activity03传过来的值:"+"\n"+value.toString());
			break;
			
		}
	}
}