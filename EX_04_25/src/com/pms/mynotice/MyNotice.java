package com.pms.mynotice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyNotice extends Activity implements OnClickListener{
	/*声明对象*/
	private Button bt1, bt2, bt3, bt4, bt5;
	NotificationManager mNotiManger;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*得到Button对象*/
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        /*获得NotificationManager对象*/
		mNotiManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        
		/*绑定监听器*/
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        
    }
    /*实现点击事件*/
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bt1:
			//显示有未接电话
			showNoti(android.R.drawable.stat_notify_missed_call, "未接电话");
			break;
		case R.id.bt2:
			//显示正在通话
			showNoti(android.R.drawable.stat_sys_phone_call, "正在通话");
			break;
		case R.id.bt3:
			//显示暂挂电话
			showNoti(android.R.drawable.stat_sys_phone_call_on_hold, "暂挂电话");
			break;
		case R.id.bt4:
			//创建一个Notification对象，参数依次为通知图标，消息，时间。一般为当前时间
			Notification noti = new Notification(android.R.drawable.stat_sys_speakerphone, 
					"已开启扬声器" ,System.currentTimeMillis());
			//创建一个PendIntent
			PendingIntent pIntent = PendingIntent.getActivity(this, 0, getIntent(), 0);
			//设置Notification的详细信息
			noti.setLatestEventInfo(this, "电话", "开启扬声器", pIntent);
			//设置提醒效果为使用默认的全部效果
			noti.defaults = Notification.DEFAULT_ALL;
			//显示该消息
			mNotiManger.notify(1, noti);
			break;
		case R.id.bt5:
			//清除所有通知
			mNotiManger.cancelAll();
			break;
		}
		
	}
	
	/**
	 * 显示一个状态栏提醒
	 * @param iconId 图标的资源id
	 * @param text   消息
	 */
	public void showNoti(int iconId, String text)
	{
		//创建Notification对象
		Notification notification = new Notification();
		//设置Notification的图标
		notification.icon = iconId;
		//设置Notification的消息
		notification.tickerText = text;
		//创建一个pendingIntent
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, getIntent(), 0);
		//设置Notification的详细信息
		notification.setLatestEventInfo(this, "电话", text, pIntent);
		//显示该消息
		mNotiManger.notify(0, notification);
	}
}