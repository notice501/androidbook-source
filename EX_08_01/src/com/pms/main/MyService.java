package com.pms.main;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
	@Override
	public IBinder onBind(Intent arg0) {
		System.out.println("onBind");
		return null;
	}
	@Override
	public void onCreate() {
		System.out.println("onCreate");
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		System.out.println("onDestroy");
		super.onDestroy();
	}
	@Override
	public void onRebind(Intent intent) {
		System.out.println("onRebind");
		super.onRebind(intent);
	}
	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println("onStart");
		super.onStart(intent, startId);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out
				.println("onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("onUnbind");
		return super.onUnbind(intent);
	}
	public class LocalBinder extends Binder {
		MyService getService() {
			return MyService.this;
		}
	}

}
