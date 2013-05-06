package com.pms.appwidget;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {
	private final static String ACTION_TAG = "com.pms.appwidget.update";
	private static Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		this.context = context;
		String action = intent.getAction();
		if ("android.appwidget.action.APPWIDGET_UPDATE".equals(action)
				|| ACTION_TAG.equals(action))
			context.startService(new Intent(context, UpdateService.class));
	}

	/**
	 * 获取android当前可用内存大小
	 * 
	 * @return
	 */
	private final static String getAvailMemory() {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
	}

	/**
	 * 更新服务
	 */
	final public static class UpdateService extends Service {

		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onStart(Intent intent, int startId) {
			super.onStart(intent, startId);
			String info = null;
			// 创建RemoteViews
			RemoteViews views = new RemoteViews(getPackageName(),
					R.layout.mywidget_frame);
			try {
				info = "可用物理内存：" + String.valueOf(getAvailMemory()); // 获取CPU消耗信息
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 设置控件
			views.setTextViewText(R.id.app_frame_message, info);
			views.setImageViewResource(R.id.app_frame_icon, R.drawable.icon);
			Intent intent_b = new Intent();
			intent_b.setAction(ACTION_TAG);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
					intent_b, 0);
			// 给图片设置响应事件
			views.setOnClickPendingIntent(R.id.app_frame_icon, pendingIntent);
			// 获得组建的完整名字
			ComponentName thisWidget = new ComponentName(this,
					MyWidgetProvider.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			// 执行内容更新
			manager.updateAppWidget(thisWidget, views);
		}

	}

}
