package com.pms.myhandle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class MyHandle extends Activity {
	
	private ImageView iv;
	int what = 0;
	
	//����Handle����
	private Handler handler = new Handler(){
		
		//ʵ�ַ�������������Ϣ
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){ //�жϽ��յ���Ϣ
			case 0:
				iv.setImageResource(R.drawable.photo1);
				break;
			case 1:
				iv.setImageResource(R.drawable.photo2);
				break;
			case 2:
				iv.setImageResource(R.drawable.photo3);
				break;
				
			}
			super.handleMessage(msg);
		}
		
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //ȡ��ImageView����
        iv = (ImageView) findViewById(R.id.iv);
        
        thread.start(); //�����߳�
        
    }
    
    //�½�һ���߳�
    Thread thread = new Thread(new Runnable() {
		
    	//ʵ��run����
		@Override
		public void run() {
			while(true)
			{
				Message msg = handler.obtainMessage();
				msg.what = (what++)%3;
				handler.sendMessage(msg); //������Ϣ
				try {
	                   Thread.sleep(2000);//ֹͣ2��
	                } catch (InterruptedException e) {
	                   e.printStackTrace();
	                }
			}
		}
	}){
    	
    };
}