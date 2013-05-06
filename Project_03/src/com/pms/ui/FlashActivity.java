package com.pms.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.pms.gps.R;

public class FlashActivity extends Activity {
	int times=0;
	private Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		setContentView(R.layout.flashlayout);
		startTimer();
	}
	private void openNextPage(){
		Intent intent=new Intent(this,MyMessageListActivity.class);
		startActivity(intent);
		finish();
	}		
	private class SpinnerTask extends TimerTask {
		public void run() {
			times++;
			if(times>=4){
				timer.cancel();
				times=0;
				//四秒结束打开消息列表
				openNextPage();
			}
		}
	}
	public void startTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new SpinnerTask(), 100, 1000);
		}
	}
}
