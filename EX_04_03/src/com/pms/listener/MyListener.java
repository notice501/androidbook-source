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
	
	/*声明控件的引用*/
	private Button bt;
	private TextView tv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);//得到Button的引用
        
        /*匿名内部类作为监听器*/
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText("你点击了Button");//设置TextView所显示的内容，点击Button的时候调用该方法
				
			}
		});
        
        bt.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				tv.setText("你长按了Button");
				return true;//注意这里必须是return true
			}
		});
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				tv.setText("你按下了上方向键");//监听按下下方向键的动作
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				tv.setText("你按下了下方向键");//监听按下上方向键的动作
				break;
							
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				tv.setText("你松开了上方向键");//监听松开上方向键的动作
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				tv.setText("你松开了下方向键");//监听松开下方向键的动作
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
				tv.setText("你滑动了屏幕");
				break;
			case MotionEvent.ACTION_DOWN:				
				tv.setText("你点击的屏幕坐标为"+Integer.toString(x)+","+Integer.toString(y));//取得点击坐标
				break;
			case MotionEvent.ACTION_UP:
				tv.setText("你离开屏幕的坐标为"+Integer.toString(x)+","+Integer.toString(y));//取得松开是的坐标
				break;
		}
		
		return super.onTouchEvent(event);//最好为return true
	}
    
}