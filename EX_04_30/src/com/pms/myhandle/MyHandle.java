package com.pms.myhandle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class MyHandle extends Activity {
	
	private ImageView iv;
	int what = 0;
	
	//创建Handle对象
	private Handler handler = new Handler(){
		
		//实现方法用来接收消息
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){ //判断接收的消息
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
        
        //取得ImageView对象
        iv = (ImageView) findViewById(R.id.iv);
        
        thread.start(); //启动线程
        
    }
    
    //新建一个线程
    Thread thread = new Thread(new Runnable() {
		
    	//实现run方法
		@Override
		public void run() {
			while(true)
			{
				Message msg = handler.obtainMessage();
				msg.what = (what++)%3;
				handler.sendMessage(msg); //发送消息
				try {
	                   Thread.sleep(2000);//停止2秒
	                } catch (InterruptedException e) {
	                   e.printStackTrace();
	                }
			}
		}
	}){
    	
    };
}