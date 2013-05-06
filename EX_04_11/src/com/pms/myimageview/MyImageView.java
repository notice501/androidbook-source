package com.pms.myimageview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyImageView extends Activity {
	
	private ImageView iv2;
	private ImageView iv3;
	private int image_alpha = 255;//����һ�����������alphaֵ
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        
        iv2.setOnClickListener(new OnClickListener() {
			int flag = 0;//��־Ϊ����ʵ�ֵ���л�
			@Override
			public void onClick(View v) {
				if(flag==0)
				{
					iv2.setImageResource(R.drawable.im);//����ͼƬ
					flag = 1;//��־λ��1
				}
				else
				{
					iv2.setImageDrawable(getResources().getDrawable(R.drawable.icon));//����ͼƬ����һ�ַ�ʽ
					flag = 0;//��־λ��0
				}
				
			}
		});
        
        iv3.setImageResource(R.drawable.im);//����ͼƬ����
        iv3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iv3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
							image_alpha -= 30;//��alphaֵ��30
							iv3.setAlpha(image_alpha);//����ͼƬ��alphaֵ
						
					}
				});
				
			}
		});
    }
}