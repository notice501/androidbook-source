package com.pms.myimagebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MyImageButton extends Activity {
	private ImageButton ib;         //声明ImageButton的引用
	int myflag = 0;                 //定义一个标志位来实现点击切换图像
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ib = (ImageButton) findViewById(R.id.ib);//得到ImageButton的引用
        /*添加监听事件*/
        ib.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(myflag==0){
					ib.setImageResource(R.drawable.left);//动态指定图像
					myflag = 1;//标志位置1
				}
				else{
					ib.setImageResource(R.drawable.right);
					myflag = 0;
				}
			}
        	
        });
        
    }
}