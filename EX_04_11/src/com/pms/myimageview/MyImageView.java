package com.pms.myimageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyImageView extends Activity {
	
	private ImageView iv2;
	private ImageView iv3;
	private int image_alpha = 255;//设置一个变量来存放alpha值
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        
        iv2.setOnClickListener(new OnClickListener() {
			int flag = 0;//标志为用来实现点击切换
			@Override
			public void onClick(View v) {
				if(flag==0)
				{
					iv2.setImageResource(R.drawable.im);//设置图片
					flag = 1;//标志位置1
				}
				else
				{
					iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon));//设置图片的另一种方式
					flag = 0;//标志位置0
				}
				
			}
		});
        
        iv3.setImageResource(R.drawable.im);//设置图片内容
        iv3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iv3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
							image_alpha -= 30;//将alpha值减30
							iv3.setAlpha(image_alpha);//设置图片的alpha值
						
					}
				});
				
			}
		});
    }
}