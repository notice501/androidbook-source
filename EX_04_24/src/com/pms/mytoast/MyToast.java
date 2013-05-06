package com.pms.mytoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MyToast extends Activity {
	
	/*声明Button对象*/
	private Button bt_default;
	private Button bt_custom;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*得到Button对象*/
        bt_default = (Button) findViewById(R.id.bt_default);
        bt_custom = (Button) findViewById(R.id.bt_custom);
        
        /*监听点击事件*/
        bt_default.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//显示Toast
				Toast.makeText(MyToast.this, "默认的Toast", Toast.LENGTH_LONG).show();
				
			}
		});
        /*监听点击事件*/
        bt_custom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//导入布局
				View toastView = getLayoutInflater().inflate(R.layout.mytoast, null);
				//得到Toast对象
				Toast mToast = new Toast(MyToast.this);
				//设置Toast的位置，三个参数分别为位置，x轴偏移，y轴偏移
				mToast.setGravity(Gravity.CENTER, 0, 0);
				//设置Toast的展示时间长度
				mToast.setDuration(Toast.LENGTH_SHORT);
				//设置Toast所要展示的视图
				mToast.setView(toastView);
				//显示Toast
				mToast.show();
				
			}
		});
        
    }

}