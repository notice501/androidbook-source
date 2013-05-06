package com.pms.listener;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyListener extends Activity {
	
	/*�����ؼ�������*/
	private Button bt;
	private TextView tv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);//�õ�Button������
        
        /*�����ڲ�����Ϊ������*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText("������Button");//����TextView����ʾ�����ݣ����Button��ʱ����ø÷���
				
			}
		});
        
        bt.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				tv.setText("�㳤����Button");
				return true;//ע�����������return true
			}
		});
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				tv.setText("�㰴�����Ϸ����");//���������·�����Ķ���
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				tv.setText("�㰴�����·����");//���������Ϸ�����Ķ���
				break;
							
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				tv.setText("���ɿ����Ϸ����");//�����ɿ��Ϸ�����Ķ���
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				tv.setText("���ɿ����·����");//�����ɿ��·�����Ķ���
				break;
							
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_MOVE:
				tv.setText("�㻬������Ļ");
				break;
			case MotionEvent.ACTION_DOWN:				
				tv.setText("��������Ļ����Ϊ"+Integer.toString(x)+","+Integer.toString(y));//ȡ�õ������
				break;
			case MotionEvent.ACTION_UP:
				tv.setText("���뿪��Ļ������Ϊ"+Integer.toString(x)+","+Integer.toString(y));//ȡ���ɿ��ǵ�����
				break;
		}
		
		return super.onTouchEvent(event);//���Ϊreturn true
	}
    
}