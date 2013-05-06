package com.pms.sensor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;

public class ShakeSensorActivity extends Activity implements SensorListener {
	private float lastX;
	private float lastY;
	private float lastZ;
	private View mainView;
	private long currTime;
	private long lastTime;
	private long duration;// ����ʱ�䡡
	private float currShake;
	private float totalShake;
	private ITelephony iTelephony;
	private boolean isCalling = false;
	SensorManager sm = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainView = LinearLayout.inflate(this, R.layout.main, null);
		setContentView(mainView);
		((Button) mainView.findViewById(R.id.call)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// ��绰
						callPhoneNumber10086();
					}
				});
		((Button) mainView.findViewById(R.id.stop)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// �һ�
						closePhone();
					}
				});
		// ��ȡ������������
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// ע����ٶȴ�������
		sm.registerListener(this, SensorManager.SENSOR_ACCELEROMETER,SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		sm.unregisterListener(this);// ע������

	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		// ���ȸı䣬���Բ����κβ�����һ��û��
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		float x = values[0];
		float y = values[1];
		float z = values[2];
		currTime = System.currentTimeMillis();
		if (lastX == 0 && lastY == 0 && lastZ == 0) {
			// ��һ��shake
			lastTime = currTime;
		}
		if (currTime - lastTime > 200) {// 200������һ��
			duration = currTime - lastTime;
			currShake = (Math.abs(x - lastX) + Math.abs(y - lastY) + Math.abs(z	- lastZ))/ duration * 200;
		}
		totalShake = totalShake + currShake;
		if (totalShake > 100) {
			totalShake = 0;// ����Ϊ0�������ۼƼ���
			lastX = 0;
			lastY = 0;
			lastZ = 0;
			lastTime = 0;
			currTime = 0;
			if (!isCalling) {
				callPhoneNumber10086();
				((TextView) mainView.findViewById(R.id.state)).setText("��ǰ״̬��ͨ����...");
			} else {
				closePhone();
				((TextView) mainView.findViewById(R.id.state)).setText("��ǰ״̬��ͨ������...");
			}
		}
		lastX = x;
		lastY = y;
		lastZ = z;
		lastTime = currTime;
	}

	/**
	 * tell 10086��ͨ������
	 */
	private synchronized void callPhoneNumber10086() {
		isCalling = true;
		Intent myIntentDial = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + 10086));
		startActivity(myIntentDial);
	}

	/**
	 * ����ͨ��
	 */
	private synchronized void closePhone() {
		try {
			getTelephony();
			iTelephony.endCall();			
			isCalling = false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * ��ʼ�绰��ʵ��
	 */
	public void getTelephony() {

		TelephonyManager telMgr = (TelephonyManager) this.getSystemService(Service.TELEPHONY_SERVICE);
		Class<TelephonyManager> c = TelephonyManager.class;
		Method getITelephonyMethod = null;
		try {
			getITelephonyMethod = c.getDeclaredMethod("getITelephony",(Class[]) null);
			getITelephonyMethod.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		try {
			iTelephony = (ITelephony) getITelephonyMethod.invoke(telMgr,(Object[]) null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

}