package com.pms.myimagebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MyImageButton extends Activity {
	private ImageButton ib;         //����ImageButton������
	int myflag = 0;                 //����һ����־λ��ʵ�ֵ���л�ͼ��
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ib = (ImageButton) findViewById(R.id.ib);//�õ�ImageButton������
        /*��Ӽ����¼�*/
        ib.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(myflag==0){
					ib.setImageResource(R.drawable.left);//��ָ̬��ͼ��
					myflag = 1;//��־λ��1
				}
				else{
					ib.setImageResource(R.drawable.right);
					myflag = 0;
				}
			}
        	
        });
        
    }
}