package com.pms.ui;

import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pms.data.Message;
import com.pms.scan.R;

public class MyMessageListAdapter extends BaseAdapter{
	private Context context;
	private Vector<Message> messageList=new Vector<Message>();
	public MyMessageListAdapter(Context context,Vector<Message> message){
		this.context=context;
		this.messageList=message;
	}
	@Override
	public int getCount() {
		return messageList.size();
	}

	@Override
	public Object getItem(int position) {
		return messageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=LayoutInflater.from(context).inflate(R.layout.messagelist, null);
		if(messageList.size()==0){
			return null;
		}
		Message message=messageList.elementAt(position);
		String state_text="";
		switch(message.state){
		case 0:
			state_text="扫描";
			((TextView)view.findViewById(R.id.code_text)).setTextColor(Color.GREEN);
			break;
		case 1:
			state_text="手录";
			((TextView)view.findViewById(R.id.code_text)).setTextColor(Color.BLUE);
			break;
		case 2:
			state_text="删除";
			((TextView)view.findViewById(R.id.code_text)).setTextColor(Color.RED);
			break;
		}
		((TextView)view.findViewById(R.id.state_text)).setText(state_text);
		((TextView)view.findViewById(R.id.code_text)).setText(message.codetext);
		return view;
	}
}
