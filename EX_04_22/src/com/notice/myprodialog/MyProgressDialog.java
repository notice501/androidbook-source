package com.notice.myprodialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyProgressDialog extends Activity {
	
	/*声明控件对象*/
	private Button bt1;
	private Button bt2;
	private ProgressDialog pd;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*得到控件对象*/
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        /*为Button绑定点击监听器*/
        bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 // 创建ProgressDialog对象  
                pd = new ProgressDialog(MyProgressDialog.this);  
                // 设置进度条风格为圆形
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
                // 设置进度条标题  
                pd.setTitle("标题");  
                // 设置进度条 提示信息  
                pd.setMessage("圆形进度条对话框");  
                // 设置进度条图标  
                pd.setIcon(R.drawable.icon);  
                // 设置进度条的进度条是否不确定 
                pd.setIndeterminate(true);  
                // 设置对话框是否可以按退回键取消  
                pd.setCancelable(true);
                // 设置一个Button
                pd.setButton("关闭", new DialogInterface.OnClickListener(){
                	@Override
                	public void onClick(DialogInterface dialog, int which) {
                		pd.dismiss();//关闭对话框
                		
                	}
                }
                	);
                // 让显示对话框  
                pd.show();  
            }  
		});
        /*为Button绑定点击监听器*/
        bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 创建ProgressDialog对象  
                pd = new ProgressDialog(MyProgressDialog.this);  
                // 设置进度条风格为水平长条形
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
                // 设置进度条标题  
                pd.setTitle("标题");  
                // 设置进度条提示信息 
                pd.setMessage("这是一个长条形进度条对话框");  
                // 设置进度条图标  
                pd.setIcon(R.drawable.icon);  
                // 设置进度条是否不明确   
                pd.setIndeterminate(false);  
                // 设置进度条最大进度  
                pd.setMax(100);
                // 设置底层进度条进度                
                pd.setSecondaryProgress(50);
                // 设置对话框是否可以按退回键取消  
                pd.setCancelable(true);
                // 设置一个Button
                pd.setButton("关闭", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						pd.dismiss();//关闭对话框
						
					}
				});
                // 让ProgressDialog显示  
                pd.show();  
				
			}
        });
    }
}