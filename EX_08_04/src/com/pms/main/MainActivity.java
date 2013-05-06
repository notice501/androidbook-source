package com.pms.main;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.view.WindowManager;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//获得音频服务
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//获取WIFI管理服务
		WifiManager wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
		//获取窗口管理服务
		WindowManager windowManager=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		//还有很多服务
	}
}