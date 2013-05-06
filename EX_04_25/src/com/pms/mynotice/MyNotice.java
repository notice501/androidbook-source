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
	/*��������*/
	private Button bt1, bt2, bt3, bt4, bt5;
	NotificationManager mNotiManger;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*�õ�Button����*/
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        /*���NotificationManager����*/
		mNotiManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        
		/*�󶨼�����*/
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        
    }
    /*ʵ�ֵ���¼�*/
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bt1:
			//��ʾ��δ�ӵ绰
			showNoti(android.R.drawable.stat_notify_missed_call, "δ�ӵ绰");
			break;
		case R.id.bt2:
			//��ʾ����ͨ��
			showNoti(android.R.drawable.stat_sys_phone_call, "����ͨ��");
			break;
		case R.id.bt3:
			//��ʾ�ݹҵ绰
			showNoti(android.R.drawable.stat_sys_phone_call_on_hold, "�ݹҵ绰");
			break;
		case R.id.bt4:
			//����һ��Notification���󣬲�������Ϊ֪ͨͼ�꣬��Ϣ��ʱ�䡣һ��Ϊ��ǰʱ��
			Notification noti = new Notification(android.R.drawable.stat_sys_speakerphone, 
					"�ѿ���������" ,System.currentTimeMillis());
			//����һ��PendIntent
			PendingIntent pIntent = PendingIntent.getActivity(this, 0, getIntent(), 0);
			//����Notification����ϸ��Ϣ
			noti.setLatestEventInfo(this, "�绰", "����������", pIntent);
			//��������Ч��Ϊʹ��Ĭ�ϵ�ȫ��Ч��
			noti.defaults = Notification.DEFAULT_ALL;
			//��ʾ����Ϣ
			mNotiManger.notify(1, noti);
			break;
		case R.id.bt5:
			//�������֪ͨ
			mNotiManger.cancelAll();
			break;
		}
		
	}
	
	/**
	 * ��ʾһ��״̬������
	 * @param iconId ͼ�����Դid
	 * @param text   ��Ϣ
	 */
	public void showNoti(int iconId, String text)
	{
		//����Notification����
		Notification notification = new Notification();
		//����Notification��ͼ��
		notification.icon = iconId;
		//����Notification����Ϣ
		notification.tickerText = text;
		//����һ��pendingIntent
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, getIntent(), 0);
		//����Notification����ϸ��Ϣ
		notification.setLatestEventInfo(this, "�绰", text, pIntent);
		//��ʾ����Ϣ
		mNotiManger.notify(0, notification);
	}
}