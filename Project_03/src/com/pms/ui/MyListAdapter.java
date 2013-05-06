package com.pms.ui;

import java.util.Vector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pms.data.ReceiveMessage;
import com.pms.gps.R;

public class MyListAdapter extends BaseAdapter{
	Context context=null;
	Vector<ReceiveMessage> messList=null;
	public MyListAdapter(Context context,Vector<ReceiveMessage> messList){
		this.context=context;
		this.messList=messList;
	}
	public int getCount() {
		return messList.size();
	}
	public Object getItem(int index) {
		return messList.elementAt(index);
	}
	public long getItemId(int position) {
		return 0;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=LinearLayout.inflate(context, R.layout.listitem, null);
		String title[]=messList.elementAt(position).message.split(",");		
		String time=messList.elementAt(position).time;
		int state=messList.elementAt(position).state;
		((TextView)view.findViewById(R.id.itemtitle)).setText(title[1]);
		((TextView)view.findViewById(R.id.itemstate)).setText((state==0?"未处理":"已处理")+"     "+time);
		((ImageView)view.findViewById(R.id.itemico)).setBackgroundResource((state==0?R.drawable.weidu:R.drawable.read2));
		return view;
	}
}
