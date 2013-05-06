package com.pms.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class CameraActivity extends Activity {
	private CameraView cameraView = null;// 相机操作View

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cameraView=new CameraView(this);
		setContentView(cameraView);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 当点击返回键时触发照相
		if (keyCode == 4) {
			return cameraView.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}
}