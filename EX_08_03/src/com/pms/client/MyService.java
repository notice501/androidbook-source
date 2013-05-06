package com.pms.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.pms.AidlService;
import com.pms.AidlService.Stub;

public class MyService extends Service {
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	
	//实现AIDL接口中各个方法
	private AidlService.Stub binder = new Stub() {
		@Override
		public String getMyName() throws RemoteException {
			return "我是另外一个应用的数据";
		}
	};
}
