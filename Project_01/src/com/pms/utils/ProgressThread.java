package com.pms.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.pms.rights.HomeActivity;

/**
 * Handler绑定类
 * 
 * @author machao
 * @mail zeusmc.163.com
 * 
 */
public class ProgressThread extends Thread {
	private Handler handler;
	private ProgressDialog pd;
	private int handlerWhat;
	private Message message;

	public ProgressThread(Context context, Handler handler, int handlerWhat) {
		this.handler = handler;
		this.pd = new ProgressDialog(context);
		pd.setTitle("正在加载...");
		this.handlerWhat = handlerWhat;
		pd.show();
		handler.sendEmptyMessage(HomeActivity.showProgressHandlerWhat);
	}

	public ProgressThread(Context context, Handler handler, int handlerWhat,
			String msg) {
		this.handler = handler;
		this.pd = new ProgressDialog(context);
		pd.setTitle("提示");
		pd.setMessage(msg);
		this.handlerWhat = handlerWhat;
		pd.show();
		handler.sendEmptyMessage(HomeActivity.showProgressHandlerWhat);
	}

	public ProgressThread(Context context, Handler handler, Message message,
			String msg) {
		this.handler = handler;
		this.message = message;
		this.pd = new ProgressDialog(context);
		pd.setTitle("提示");
		pd.setMessage(msg);
		pd.show();
		handler.sendEmptyMessage(HomeActivity.showProgressHandlerWhat);
	}

	public ProgressThread(Context context, Handler handler, int handlerWhat,
			String msg, Object obj) {
		this.handler = handler;
		this.message = new Message();
		this.handlerWhat = handlerWhat;
		this.pd = new ProgressDialog(context);
		pd.setTitle("提示");
		pd.setMessage(msg);
		pd.show();
		message.obj = obj;
		message.what = handlerWhat;
		handler.sendEmptyMessage(HomeActivity.showProgressHandlerWhat);
	}

	@Override
	public void run() {
		super.run();
		if (message == null) {
			handler.sendEmptyMessage(handlerWhat);
		} else {
			handler.sendMessage(message);
		}
		pd.dismiss();
	}
}
